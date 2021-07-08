package com.bmc.appointmentservice.controller;

import com.bmc.appointmentservice.model.Appointment;
import com.bmc.appointmentservice.model.Prescription;
import com.bmc.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/user/{userId}/appointment")
    public ResponseEntity<String> bookAppointment(@PathVariable String userId, @RequestBody Appointment appointment){
        appointment.setUserId(userId);
        return ResponseEntity.ok(appointmentService.appointment(appointment));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/{userId}/prescription")
    public ResponseEntity<String> prescription(@PathVariable String userId, @RequestBody Prescription prescription){
        return ResponseEntity.ok("Success");
    }
}