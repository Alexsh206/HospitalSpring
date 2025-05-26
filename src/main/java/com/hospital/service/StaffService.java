package com.hospital.service;

import com.hospital.model.Staff;
import com.hospital.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    private final StaffRepository repo;

    public StaffService(StaffRepository repo) {
        this.repo = repo;
    }

    public List<Staff> findAll() {
        return repo.findAll();
    }

    public Optional<Staff> findById(Integer id) {
        return repo.findById(id);
    }

    public Staff create(Staff s) {
        return repo.save(s);
    }

    public Staff update(Staff s) {
        return repo.save(s);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Optional<Staff> login(String phone, String password) {
        return repo.findByPhoneAndPassword(phone, password);
    }
}