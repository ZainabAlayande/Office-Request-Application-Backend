package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
