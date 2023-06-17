package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
