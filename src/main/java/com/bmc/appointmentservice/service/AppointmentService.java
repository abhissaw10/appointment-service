package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.entity.Availability;
import com.bmc.appointmentservice.model.Appointment;
import com.bmc.appointmentservice.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorAvailabilityService availabilityService;

    private final NotificationService notificationService;

    public String appointment(Appointment appointment){
        appointment.setAppointmentId(UUID.randomUUID().toString());
        appointment.setCreatedDate(LocalDateTime.now().toString());
        appointment.setStatus("PendingPayment");
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

    private void notify(Appointment appointment){

        notificationService.notifyAppointmentConfirmation(appointment);
    }
}
