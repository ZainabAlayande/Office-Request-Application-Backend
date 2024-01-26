package africa.semicolon.remApp.services.superAdmin;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.CompanyRegistrationResponse;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.SuperAdmin;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class SuperAdminUtils {

    private final PasswordEncoder passwordEncoder;





//    public SuperAdmin buildSuperAdminInformation(CompanyRegistrationRequest request, String companyId) {
//        SuperAdmin superAdmin = new SuperAdmin();
//        superAdmin.setEmail(request.getEmail());
//        superAdmin.setFirstName(request.getSuperAdminFirstName());
//        superAdmin.setLastName(request.getSuperAdminLastName());
//        superAdmin.setPassword(passwordEncoder.encode(request.getSuperAdminPassword()));
//        superAdmin.setCompanyId(companyId);
//        superAdmin.setTimeCreated(LocalDateTime.now());
//        superAdmin.setRoles(List.of(Role.SUPER_ADMIN));
//        return superAdmin;
//
//    }


}
