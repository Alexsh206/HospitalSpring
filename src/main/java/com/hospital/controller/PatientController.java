// src/main/java/com/hospital/controller/PatientController.java
package com.hospital.controller;

import com.hospital.model.Patient;
import com.hospital.service.PatientService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET  /api/patients
    @GetMapping
    public List<Patient> getAll() {
        return patientService.findAll();
    }

    // GET  /api/patients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Integer id) {
        return patientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/patients
    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody Patient p) {
        Patient created = patientService.create(p);
        return ResponseEntity.ok(created);
    }

    // PUT  /api/patients/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Integer id,
                                          @RequestBody Patient p) {
        return patientService.findById(id)
                .map(existing -> {
                    p.setId(id);
                    Patient updated = patientService.update(p);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/patients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (patientService.findById(id).isPresent()) {
            patientService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/patients/login
    @PostMapping("/login")
    public ResponseEntity<Patient> login(@RequestBody LoginRequest creds) {
        return patientService.login(creds.getPhone(), creds.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    // DTO for login credentials
    @Setter
    @Getter
    public static class LoginRequest {
        private String phone;
        private String password;

    }
}
