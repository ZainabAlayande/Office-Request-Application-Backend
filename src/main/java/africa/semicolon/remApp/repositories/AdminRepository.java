package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
