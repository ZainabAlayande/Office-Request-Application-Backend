package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyEmail(String email);

}
