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
    @PostMapping("/appointment")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment){
        return ResponseEntity.ok(appointmentService.appointment(appointment));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/prescription")
    public ResponseEntity prescription(@RequestBody Prescription prescription){
        appointmentService.prescription(prescription);
        return ResponseEntity.ok().build();
    }
}