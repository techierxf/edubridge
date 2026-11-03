
package edubridge.controller;


import edubridge.repository.StudentEnrollmentRepository;
import edubridge.repository.StudentAccountRepository;
import edubridge.repository.StaffAccountRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/staff/applications")
public class StaffApplicationController {

    private final StudentEnrollmentRepository repository;
    private final StudentAccountRepository studentAccountRepository;
    private final StaffAccountRepository staffAccountRepository;
   private boolean isStaffLoggedIn(HttpSession session) {
    Long staffId = (Long) session.getAttribute("staffId");

    if (staffId == null) {
        return false;
    }

    return staffAccountRepository.findById(staffId).isPresent();
}

    // One constructor for both repositories
    public StaffApplicationController(
        StudentEnrollmentRepository repository,
        StudentAccountRepository studentAccountRepository,
        StaffAccountRepository staffAccountRepository) {

    this.repository = repository;
    this.studentAccountRepository = studentAccountRepository;
    this.staffAccountRepository = staffAccountRepository;
}
    public record ApplicationSummary(
            Long id,
            String referenceNo,
            String fullName,
            String courseId,
            LocalDateTime createdAt,
            String status
    ) {}
    public record RecentStudent(
        Long studentId,
        String fullName,
        String studentType,
        String courseId,
        Integer yearLevel,
        String status
) {}
    public record StudentRecord(
        Long studentId,
        String fullName,
        String studentType,
        String courseId,
        Integer yearLevel,
        String status
) {}

    @GetMapping
public ResponseEntity<?> getAllApplications(HttpSession session) {

    if (!isStaffLoggedIn(session)) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Please log in as staff."));
    }

    List<ApplicationSummary> applications = repository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt")
    ).stream().map(application ->
            new ApplicationSummary(
                    application.getId(),
                    application.getReferenceNo(),
                    application.getFullName(),
                    application.getCourseId(),
                    application.getCreatedAt(),
                    application.getStatus()
            )
    ).toList();

    return ResponseEntity.ok(applications);
}

    // Dashboard statistics
    @GetMapping("/stats")
public ResponseEntity<?> getDashboardStats(HttpSession session) {

    if (!isStaffLoggedIn(session)) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Please log in as staff."));
    }

    long totalStudents = studentAccountRepository.count();
    long enrolledStudents = repository.countByStatus("APPROVED");
    long pendingApplications = repository.countByStatus("PENDING");
    long rejectedApplications = repository.countByStatus("REJECTED");

    return ResponseEntity.ok(Map.of(
            "totalStudents", totalStudents,
            "enrolledStudents", enrolledStudents,
            "pendingApplications", pendingApplications,
            "rejectedApplications", rejectedApplications
    ));
}
    public record ReviewRequest(
            String status,
            String remarks
    ) {}

    @GetMapping("/{id}")
public ResponseEntity<?> getApplication(
        @PathVariable Long id,
        HttpSession session) {

    if (!isStaffLoggedIn(session)) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Please log in as staff."));
    }

    return repository.findById(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
}
       

@PostMapping("/{id}/decision")
public ResponseEntity<?> updateApplicationStatus(
        @PathVariable Long id,
        @RequestBody ReviewRequest request,
        HttpSession session) {

    // Check staff session
    if (!isStaffLoggedIn(session)) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Please log in as staff."));
    }

    // Validate decision
    if (request.status() == null ||
            (!request.status().equals("APPROVED")
            && !request.status().equals("REJECTED"))) {

        return ResponseEntity.badRequest()
                .body(Map.of("message", "Invalid application status."));
    }

    // Validate remarks length
    if (request.remarks() != null &&
            request.remarks().length() > 1000) {

        return ResponseEntity.badRequest()
                .body(Map.of("message", "Remarks must not exceed 1000 characters."));
    }

    return repository.findById(id)
            .map(application -> {

                application.setStatus(request.status());
                application.setRemarks(request.remarks());

                repository.save(application);

                return ResponseEntity.ok(
                        Map.of("message",
                                "Application status updated successfully!")
                );
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
}

   @GetMapping("/recent")
public ResponseEntity<?> getRecentStudents(HttpSession session) {

    if (!isStaffLoggedIn(session)) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Please log in as staff."));
    }

    List<RecentStudent> students = repository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt")
    ).stream()
            .limit(5)
            .map(application -> new RecentStudent(
                    application.getStudentAccountId(),
                    application.getFullName(),
                    application.getStudentType(),
                    application.getCourseId(),
                    application.getYearLevel(),
                    application.getStatus()
            ))
            .toList();

    return ResponseEntity.ok(students);
}
@GetMapping("/records")
public ResponseEntity<?> getAllStudentRecords(HttpSession session) {

    if (!isStaffLoggedIn(session)) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Please log in as staff."));
    }

    List<StudentRecord> records = repository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt")
    ).stream()
            .map(application -> new StudentRecord(
                    application.getStudentAccountId(),
                    application.getFullName(),
                    application.getStudentType(),
                    application.getCourseId(),
                    application.getYearLevel(),
                    application.getStatus()
            ))
            .toList();

    return ResponseEntity.ok(records);
}
}
