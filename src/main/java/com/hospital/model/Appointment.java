package com.hospital.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    private String diagnosis;
    private String medication;

    @Column(name = "procedure_name")
    private String procedureName;

    private String surgery;
    private String status;

    // зв’язок із таблицею staff (лікар)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Staff staff;

    // зв’язок із таблицею patients (пацієнт)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
