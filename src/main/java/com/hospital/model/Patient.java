package com.hospital.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    private String patronymic;
    private String sex;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String phone;

    // не віддаємо пароль у JSON
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // (опціонально) список призначень пацієнта
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;
}
