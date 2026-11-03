
package edubridge.controller;

import edubridge.entity.StaffAccount;
import edubridge.repository.StaffAccountRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/staff")
public class StaffSetupController {

    private final StaffAccountRepository repository;
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public StaffSetupController(StaffAccountRepository repository) {
        this.repository = repository;
    }

    public record StaffSetupRequest(
            String fullName,
            String email,
            String password
    ) {}

    @PostMapping("/setup")
    public ResponseEntity<?> createFirstStaff(
            @RequestBody StaffSetupRequest request) {

        if (repository.count() > 0) {
            return ResponseEntity.status(403)
                    .body(Map.of("message",
                            "Staff account setup is already completed."));
        }

        if (request.fullName() == null ||
                request.email() == null ||
                request.password() == null ||
                request.password().length() < 8) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Complete all fields. Password must be at least 8 characters."));
        }

        StaffAccount staff = new StaffAccount();
        staff.setFullName(request.fullName());
        staff.setEmail(request.email());
        staff.setPasswordHash(
                encoder.encode(request.password())
        );

        repository.save(staff);

        return ResponseEntity.ok(
                Map.of("message",
                        "First staff account created successfully!")
        );
    }
}
