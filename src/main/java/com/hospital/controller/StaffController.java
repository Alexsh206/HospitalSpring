package com.hospital.controller;

import com.hospital.model.Staff;
import com.hospital.service.StaffService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // GET  /api/staff
    @GetMapping
    public List<Staff> getAll() {
        return staffService.findAll();
    }

    // GET  /api/staff/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getById(@PathVariable Integer id) {
        return staffService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/staff
    @PostMapping
    public ResponseEntity<Staff> create(@RequestBody Staff s) {
        Staff created = staffService.create(s);
        return ResponseEntity.ok(created);
    }

    // PUT  /api/staff/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Staff> update(@PathVariable Integer id,
                                        @RequestBody Staff s) {
        return staffService.findById(id)
                .map(existing -> {
                    s.setId(id);
                    Staff updated = staffService.update(s);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/staff/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return staffService.findById(id)
                .map(existing -> {
                    staffService.delete(id);
                    return ResponseEntity.<Void>ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/staff/login
    @PostMapping("/login")
    public ResponseEntity<Staff> login(@RequestBody LoginRequest creds) {
        return staffService
                .login(creds.getPhone(), creds.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    // Внутрішній DTO для логіну
    @Setter
    @Getter
    public static class LoginRequest {
        private String phone;
        private String password;

    }
}
