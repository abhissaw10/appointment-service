package com.bmc.appointmentservice.controller;

import brave.Response;
import com.bmc.appointmentservice.model.Availability;
import com.bmc.appointmentservice.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DoctorAvailabilityController {
    private final DoctorAvailabilityService availabilityService;
    @PostMapping("/doctor/{id}/availability")
    public ResponseEntity<String> saveDoctorAvailability(@PathVariable String id, @RequestBody Availability availabilityRequest){
        availabilityRequest.setDoctorId(id);
        availabilityService.saveDoctorAvailability(availabilityRequest);
    return ResponseEntity.ok("Success");
    }

    @GetMapping("/doctor/{id}/availability")
    public ResponseEntity<Availability> getDoctorAvailability(@PathVariable String id){
        return ResponseEntity.ok(availabilityService.getDoctorAvailability(id));
    }
}
