package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.entity.Availability;
import com.bmc.appointmentservice.model.Appointment;
import com.bmc.appointmentservice.model.AppointmentStatus;
import com.bmc.appointmentservice.model.Prescription;
import com.bmc.appointmentservice.model.User;
import com.bmc.appointmentservice.repository.AppointmentRepository;
import com.bmc.appointmentservice.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.bmc.appointmentservice.model.AppointmentStatus.PendingPayment;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorAvailabilityService availabilityService;

    private final NotificationService notificationService;

    private final PrescriptionRepository prescriptionRepository;

    private final UserClient userClient;

    public String appointment(Appointment appointment){
        appointment.setAppointmentId(UUID.randomUUID().toString());
        appointment.setCreatedDate(LocalDateTime.now().toString());
        appointment.setStatus(PendingPayment.name());
        List<Availability> availabilities = availabilityService.getAvailabilities(appointment.getDoctorId());
        boolean slotFound = false;
        for(Availability availability: availabilities){
            if(availability.getAvailabilityDate().equals(appointment.getAppointmentDate())
            && availability.getTimeSlot().equals(appointment.getTimeSlot())) {
                if (!availability.isBooked()) {
                    availability.setBooked(true);
                    availabilityService.updateAvailability(availability);

                } else {
                    //TODO throw exception saying slot already booked.
                }
                slotFound=true;
                break;
            }
        }
        if(slotFound) {
            appointmentRepository.save(appointment);
            notify(appointment);
        }else{
            //TODO Not an available Slot exception
        }
        return appointment.getAppointmentId();
    }

    public void prescription(Prescription prescription){
        prescription.setId(UUID.randomUUID().toString());
        prescriptionRepository.save(prescription);
        notify(prescription);
    }

    private void notify(Appointment appointment){
        notificationService.notifyAppointmentConfirmation(appointment);
    }

    private void notify(Prescription prescription){
        ResponseEntity<User> userEntity = userClient.getUser(prescription.getUserId());
        prescription.setUserEmailId(userEntity.getBody().getEmailId());
        notificationService.notifyPrescription(prescription);
    }
}
