
package edubridge.controller;

import edubridge.entity.StaffAccount;
import edubridge.repository.StaffAccountRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/staff")
public class StaffLoginController {

    private final StaffAccountRepository repository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public StaffLoginController(StaffAccountRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody StaffLoginRequest request,
            HttpSession session) {

        if (request.email() == null ||
                request.password() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Please enter your email and password."));
        }

        StaffAccount staff = repository
                .findByEmail(request.email())
                .orElse(null);

        if (staff == null ||
                !passwordEncoder.matches(
                        request.password(),
                        staff.getPasswordHash())) {

            return ResponseEntity.status(401)
                    .body(Map.of("message",
                            "Invalid email or password."));
        }

        session.setAttribute("staffId", staff.getId());
        session.setAttribute("staffName", staff.getFullName());
        session.setAttribute("staffRole", staff.getRole());

        return ResponseEntity.ok(
                Map.of("message",
                        "Welcome, " + staff.getFullName() + "!")
        );
    }

    public record StaffLoginRequest(
            String email,
            String password
    ) {}
}
