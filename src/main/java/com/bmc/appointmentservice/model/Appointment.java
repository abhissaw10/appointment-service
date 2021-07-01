package com.bmc.appointmentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
public class Appointment {
    @Id
    private String appointmentId;
    private String doctorId;
    private String userId;
    private String timeSlot;
    private String status;
    private LocalDate appointmentDate;
    @JsonIgnore
    private String createdDate;
    private String symptoms;
    private String priorMedicalHistory;

}
