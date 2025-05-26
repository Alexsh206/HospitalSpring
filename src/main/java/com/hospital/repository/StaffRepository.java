package com.hospital.repository;

import com.hospital.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    // метод для логіну
    Optional<Staff> findByPhoneAndPassword(String phone, String password);
}