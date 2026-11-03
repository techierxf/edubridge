
package edubridge.controller;

import edubridge.entity.StudentAccount;
import edubridge.repository.StudentAccountRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentRegistrationController {

    private final StudentAccountRepository repository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public StudentRegistrationController(
            StudentAccountRepository repository) {
        this.repository = repository;
    }

    public record SignupRequest(
            String fullName,
            String email,
            String password,
            String confirmPassword) {
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody SignupRequest request) {
         

        if (request.fullName() == null ||
            request.email() == null ||
            request.password() == null ||
            request.confirmPassword() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Please complete all fields."));
        }

        String fullName = request.fullName().trim();
        String email = request.email().trim().toLowerCase();

        if (fullName.isEmpty() || email.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Name and email are required."));
        }

        if (request.password().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Password must contain at least 8 characters."));
        }

        if (!request.password().equals(request.confirmPassword())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Passwords do not match."));
        }

        if (repository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Email is already registered."));
        }

        String hashedPassword =
                passwordEncoder.encode(request.password());

        StudentAccount student = new StudentAccount(
                fullName, email, hashedPassword);

        repository.save(student);

        return ResponseEntity.ok(
                Map.of("message",
                        "Student account created successfully!"));
    }
}
