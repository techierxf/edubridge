package edubridge.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentPortalController {

    @GetMapping("/me")
    public ResponseEntity<?> getStudentInfo(HttpSession session) {

        Long studentId = (Long) session.getAttribute("studentId");
        String studentName = (String) session.getAttribute("studentName");

        if (studentId == null || studentName == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Please log in first."));
        }

        return ResponseEntity.ok(
                Map.of(
                    "studentId", studentId,
                    "studentName", studentName
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok(
                Map.of("message", "Logged out successfully.")
        );
    }
}