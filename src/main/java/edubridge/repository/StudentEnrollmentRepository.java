package edubridge.repository;

import edubridge.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentEnrollmentRepository
        extends JpaRepository<StudentEnrollment, Long> {

    List<StudentEnrollment> findByStudentAccountId(Long studentAccountId);

    boolean existsByStudentAccountId(Long studentAccountId);
    long countByStatus(String status);
    boolean existsByStudentAccountIdAndStatusIn(
        Long studentAccountId, List<String> statuses);
}