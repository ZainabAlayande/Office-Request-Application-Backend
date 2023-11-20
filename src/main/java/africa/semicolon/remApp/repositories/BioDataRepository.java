package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.BioData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BioDataRepository extends JpaRepository<BioData, Long> {

    Optional<BioData> findByOfficeEmailAddress(String email);

    boolean existsByOfficeEmailAddress(String email);

}
