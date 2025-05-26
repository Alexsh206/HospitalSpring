package com.hospital.service;

import com.hospital.model.Patient;
import com.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> findAll() {
        return repo.findAll();                                   // замість getAllPatients() :contentReference[oaicite:5]{index=5}
    }

    public Optional<Patient> findById(Integer id) {
        return repo.findById(id);                                // замість getPatientById(...) :contentReference[oaicite:6]{index=6}
    }

    public Patient create(Patient p) {
        return repo.save(p);                                     // замість addPatient(...) :contentReference[oaicite:7]{index=7}
    }

    public Patient update(Patient p) {
        return repo.save(p);                                     // замість updatePatient(...) :contentReference[oaicite:8]{index=8}
    }

    public void delete(Integer id) {
        repo.deleteById(id);                                     // замість deletePatient(...) :contentReference[oaicite:9]{index=9}
    }

    public Optional<Patient> login(String phone, String password) {
        return repo.findByPhoneAndPassword(phone, password);
    }
}
