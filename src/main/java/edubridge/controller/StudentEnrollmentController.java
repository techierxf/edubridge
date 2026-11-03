
package edubridge.controller;

import edubridge.entity.StudentEnrollment;
import edubridge.repository.StudentEnrollmentRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class StudentEnrollmentController {

    private final StudentEnrollmentRepository repository;

    public StudentEnrollmentController(
            StudentEnrollmentRepository repository) {
        this.repository = repository;
    }

    public record EnrollmentRequest(
            String studentType,
            String academicYear,
            String semester,
            String fullName,
            LocalDate dateOfBirth,
            String sex,
            String email,
            String contactNumber,
            String completeAddress,
            String courseId,
            Integer yearLevel,
            String previousSchoolName,
            String previousCourse,
            Integer lastYearLevel,
            String guardianFullName,
            String guardianRelationship,
            String guardianContactNumber
    ) {}

    @PostMapping
    public ResponseEntity<?> submitEnrollment(
            @RequestBody EnrollmentRequest request,
            HttpSession session) {

        Long studentId = (Long) session.getAttribute("studentId");

        if (studentId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message",
                            "Please log in first."));
        }
        if (repository.existsByStudentAccountIdAndStatusIn(
        studentId, List.of("PENDING", "APPROVED"))) {

    return ResponseEntity.status(409)
            .body(Map.of("message",
                    "You already have a pending or approved application."));

}
        if (request.fullName() == null ||
            request.email() == null ||
            request.courseId() == null ||

            request.academicYear() == null ||
            request.semester() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Please complete the required fields."));
        }

        StudentEnrollment enrollment = new StudentEnrollment();

        enrollment.setStudentAccountId(studentId);
        enrollment.setStudentType(request.studentType());
        enrollment.setAcademicYear(request.academicYear());
        enrollment.setSemester(request.semester());
        enrollment.setFullName(request.fullName());
        enrollment.setDateOfBirth(request.dateOfBirth());
        enrollment.setSex(request.sex());
        enrollment.setEmail(request.email());
        enrollment.setContactNumber(request.contactNumber());
        enrollment.setCompleteAddress(request.completeAddress());
        enrollment.setCourseId(request.courseId());
        enrollment.setYearLevel(request.yearLevel());
        enrollment.setPreviousSchoolName(request.previousSchoolName());
        enrollment.setPreviousCourse(request.previousCourse());
        enrollment.setLastYearLevel(request.lastYearLevel());
        enrollment.setGuardianFullName(request.guardianFullName());
        enrollment.setGuardianRelationship(
                request.guardianRelationship());
        enrollment.setGuardianContactNumber(
                request.guardianContactNumber());

        repository.save(enrollment);

        return ResponseEntity.ok(
                Map.of("message",
                        "Enrollment application submitted successfully!")
        );
    }
    @GetMapping("/mine")
public ResponseEntity<?> getMyEnrollments(HttpSession session) {

    Long studentId = (Long) session.getAttribute("studentId");

    if (studentId == null) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Please log in first."));
    }

    List<StudentEnrollment> enrollments =
            repository.findByStudentAccountId(studentId);

    return ResponseEntity.ok(enrollments);
}
}
