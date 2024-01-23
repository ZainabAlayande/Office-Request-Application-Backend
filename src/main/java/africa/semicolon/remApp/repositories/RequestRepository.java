package africa.semicolon.remApp.repositories;

import africa.semicolon.remApp.models.Requests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Requests, Long> {
    List<Requests> findByUserId(String userId);

    List<Requests> findAllByCompanyId(String companyId);

//    void findAllRequestCount(String companyId);

}
