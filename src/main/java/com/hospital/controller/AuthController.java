package com.hospital.controller;

import com.hospital.model.Patient;
import com.hospital.model.Staff;
import com.hospital.service.PatientService;
import com.hospital.service.StaffService;
import com.hospital.config.JwtConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    private final PatientService patientService;
    private final StaffService staffService;
    private final JwtConfig jwtConfig;

    public AuthController(PatientService patientService,
                          StaffService staffService,
                          JwtConfig jwtConfig) {
        this.patientService = patientService;
        this.staffService   = staffService;
        this.jwtConfig      = jwtConfig;
    }

    // Старая ссылка: POST /api/auth/login
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> loginApi(@RequestBody LoginRequest creds) {
        return doLogin(creds);
    }

    // Новая ссылка: POST /login
    @PostMapping("/login")
    public ResponseEntity<?> loginRoot(@RequestBody LoginRequest creds) {
        return doLogin(creds);
    }

    // Вынесли общую логику в приватный метод
    private ResponseEntity<?> doLogin(LoginRequest creds) {
        // <--- здесь ваша старая логика из AuthController.login() ---
        return patientService.login(creds.getPhone(), creds.getPassword())
                .map(p -> buildResponse(p.getId(), "patient", Map.of("name", p.getFirstName() + " " + p.getLastName())))
                .or(() -> staffService.login(creds.getPhone(), creds.getPassword())
                        .map(s -> buildResponse(s.getId(), "staff",
                                Map.of("name",     s.getFirstName() + " " + s.getLastName(),
                                        "position", s.getPosition()))))
                .orElseGet(() ->
                        ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"))
                );
    }

    private ResponseEntity<Map<String,Object>> buildResponse(int id, String role, Map<String,Object> extras) {
        String token = jwtConfig.createToken(id, role);
        Map<String,Object> out = new HashMap<>(extras);
        out.put("token", token);
        out.put("role",  role);
        out.put("id",    id);
        return ResponseEntity.ok(out);
    }

    @Setter
    @Getter
    public static class LoginRequest {
        // геттеры/сеттеры
        private String phone;
        private String password;

    }
}