package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repo;

    public AppointmentService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public List<Appointment> findAll() {
        return repo.findAll();                                   // замість getAllAppointments() :contentReference[oaicite:0]{index=0}
    }

    public Optional<Appointment> findById(Integer id) {
        return repo.findById(id);                                // замість getAppointmentById(...) :contentReference[oaicite:1]{index=1}
    }

    public Appointment create(Appointment a) {
        return repo.save(a);                                     // замість addAppointment(...) :contentReference[oaicite:2]{index=2}
    }

    public Appointment update(Appointment a) {
        return repo.save(a);                                     // save() виконає UPDATE для існуючих id :contentReference[oaicite:3]{index=3}
    }

    public void delete(Integer id) {
        repo.deleteById(id);                                     // замість deleteAppointment(...) :contentReference[oaicite:4]{index=4}
    }
}
