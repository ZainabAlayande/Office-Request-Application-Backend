package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(String userId);

}
