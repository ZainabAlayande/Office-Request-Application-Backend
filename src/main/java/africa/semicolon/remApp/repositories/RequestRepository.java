package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
