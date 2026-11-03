package edubridge.controller;

import edubridge.entity.StudentAccount;
import edubridge.repository.StudentAccountRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentLoginController {

    private final StudentAccountRepository repository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public StudentLoginController(
            StudentAccountRepository repository) {
        this.repository = repository;
    }

    public record LoginRequest(
            String email,
            String password) {
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpSession session) {

        if (request.email() == null ||
            request.password() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Please enter your email and password."));
        }

        String email = request.email().trim().toLowerCase();

        StudentAccount student = repository
                .findByEmail(email)
                .orElse(null);

        if (student == null ||
            !passwordEncoder.matches(
                    request.password(),
                    student.getPasswordHash())) {

            return ResponseEntity.status(401)
                    .body(Map.of("message",
                            "Invalid email or password."));
        }

        session.setAttribute("studentId", student.getId());

        session.setAttribute(
                "studentName", student.getFullName());

        return ResponseEntity.ok(
                Map.of("message",
                        "Login successful! Welcome, "
                        + student.getFullName()));
    }
}