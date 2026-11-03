
package edubridge.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/staff")
public class StaffSessionController {

    @GetMapping("/me")
    public ResponseEntity<?> getStaffSession(HttpSession session) {

        Long staffId = (Long) session.getAttribute("staffId");
        String staffName = (String) session.getAttribute("staffName");

        if (staffId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Please log in first."));
        }

        String staffRole = (String) session.getAttribute("staffRole");

return ResponseEntity.ok(
        Map.of(
                "id", staffId,
                "fullName", staffName,
                "role", staffRole
        )

        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok(
                Map.of("message", "Staff logged out successfully.")
        );
    }
}
