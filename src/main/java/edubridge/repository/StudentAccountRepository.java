
package edubridge.repository;

import edubridge.entity.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentAccountRepository
        extends JpaRepository<StudentAccount, Long> {

    Optional<StudentAccount> findByEmail(String email);

    boolean existsByEmail(String email);
}
