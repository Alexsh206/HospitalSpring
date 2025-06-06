package com.hospital.controller;

import com.hospital.model.Patient;
import com.hospital.model.Staff;
import com.hospital.service.PatientService;
import com.hospital.service.StaffService;
import com.hospital.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ProfileController {

    private final PatientService patientService;
    private final StaffService staffService;
    private final JwtConfig jwtConfig;

    public ProfileController(PatientService patientService,
                             StaffService staffService,
                             JwtConfig jwtConfig) {
        this.patientService = patientService;
        this.staffService   = staffService;
        this.jwtConfig      = jwtConfig;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authHeader.substring(7);
        try {
            Jws<Claims> jwt    = jwtConfig.parseToken(token);
            Claims    body     = jwt.getBody();
            String    role     = body.get("role", String.class);
            int       id       = Integer.parseInt(body.getSubject());

            if ("patient".equals(role)) {
                Patient p = patientService.findById(id).orElseThrow();
                return ResponseEntity.ok(Map.of(
                        "id",   p.getId(),
                        "name", p.getFirstName() + " " + p.getLastName(),
                        "role", "patient"
                ));
            } else {
                Staff s = staffService.findById(id).orElseThrow();
                return ResponseEntity.ok(Map.of(
                        "id",       s.getId(),
                        "name",     s.getFirstName() + " " + s.getLastName(),
                        "role",     "staff",
                        "position", s.getPosition()
                ));
            }
        } catch (JwtException e) {
            // токен истёк или неверен
            return ResponseEntity.status(401).build();
        }
    }
}