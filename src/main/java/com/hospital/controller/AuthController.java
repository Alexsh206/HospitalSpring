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
@RequestMapping("/api/auth")
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest creds) {
        // Попытка аутентифицировать пациента
        return patientService.login(creds.getPhone(), creds.getPassword())
                .map(p -> buildResponse(p.getId(), "patient",
                        Map.of("name", p.getFirstName() + " " + p.getLastName())))
                // Если не пациент – пытаемся сотрудника
                .or(() -> staffService.login(creds.getPhone(), creds.getPassword())
                        .map(s -> buildResponse(s.getId(), "staff",
                                Map.of("name",     s.getFirstName() + " " + s.getLastName(),
                                        "position", s.getPosition()))))
                .orElseGet(() ->
                        ResponseEntity.status(401)
                                .body(Map.of("error", "Invalid credentials")));
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