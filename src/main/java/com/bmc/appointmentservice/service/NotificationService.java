package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.model.Appointment;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NotificationService {

    @Autowired
    KafkaTemplate<String, Appointment> kafkaTemplate;

    @Value("${doctor.registration.notification}")
    private String appointmentConfirmationTopic;

    public void notifyAppointmentConfirmation(Appointment appointment){
        log.info(appointment);
        kafkaTemplate.send(appointmentConfirmationTopic,appointment);
    }
}
