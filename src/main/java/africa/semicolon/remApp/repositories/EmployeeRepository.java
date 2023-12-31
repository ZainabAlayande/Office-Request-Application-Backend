package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByBioData(BioData bioData);
}
