
package edubridge.controller;

import edubridge.entity.StaffAccount;
import edubridge.repository.StaffAccountRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/staff/management")
public class StaffManagementController {

    private final StaffAccountRepository repository;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public StaffManagementController(
            StaffAccountRepository repository) {
        this.repository = repository;
    }

    public record CreateStaffRequest(
            String fullName,
            String email,
            String password
    ) {}

    @PostMapping("/create")
    public ResponseEntity<?> createStaff(
            @RequestBody CreateStaffRequest request,
            HttpSession session) {

        // Verify that a staff member is logged in
        Long staffId = (Long) session.getAttribute("staffId");

        if (staffId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message",
                            "Please log in as staff."));
        }

        // Get the actual staff account from the database
        StaffAccount currentStaff =
                repository.findById(staffId).orElse(null);

        if (currentStaff == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message",
                            "Staff account not found."));
        }

        // Verify ADMIN role using the database
        if (!"ADMIN".equalsIgnoreCase(currentStaff.getRole())) {
            return ResponseEntity.status(403)
                    .body(Map.of("message",
                            "Only administrators can create staff accounts."));
        }

        // Validate fields
        if (request.fullName() == null ||
                request.fullName().isBlank() ||
                request.email() == null ||
                request.email().isBlank() ||
                request.password() == null ||
                request.password().length() < 8) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Complete all fields. Password must be at least 8 characters."));
        }

        String email = request.email().trim().toLowerCase();

        if (repository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Email address is already registered."));
        }

        StaffAccount staff = new StaffAccount();
        staff.setFullName(request.fullName().trim());
        staff.setEmail(email);
        staff.setPasswordHash(
                encoder.encode(request.password()));
        staff.setRole("STAFF");

        repository.save(staff);

        return ResponseEntity.ok(
                Map.of("message",
                        "Staff account created successfully!")
        );
    }
}
