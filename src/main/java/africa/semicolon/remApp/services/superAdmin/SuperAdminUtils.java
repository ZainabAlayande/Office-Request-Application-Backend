package africa.semicolon.remApp.services.superAdmin;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.CompanyRegistrationResponse;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.SuperAdmin;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class SuperAdminUtils {

    private final PasswordEncoder passwordEncoder;



    public Company buildCompanyInformation(CompanyRegistrationRequest request) {
        Company company = new Company();
        company.setCompanyEmail(request.getCompanyEmail());
        company.setCompanySize(request.getCompanySize());
        company.setCompanyName(request.getCompanyName());
        company.setCompanyPassword(passwordEncoder.encode(request.getCompanyPassword()));
        company.setCompanyUniqueID(companyUniqueID(request.getCompanyName()));
        company.setTimeCreated(LocalDateTime.now());
        return company;
    }

    public SuperAdmin buildSuperAdminInformation(CompanyRegistrationRequest request, String companyId) {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.setEmail(request.getCompanyEmail());
        superAdmin.setFirstName(request.getSuperAdminFirstName());
        superAdmin.setLastName(request.getSuperAdminLastName());
        superAdmin.setPassword(passwordEncoder.encode(request.getSuperAdminPassword()));
        superAdmin.setCompanyId(companyId);
        superAdmin.setTimeCreated(LocalDateTime.now());
        superAdmin.setRoles(List.of(Role.SUPER_ADMIN));
        return superAdmin;

    }

    public static CompanyRegistrationResponse buildCompanyRegistrationResponse(Company mappedCompany, SuperAdmin mappedSuperAdmin) {
        return  CompanyRegistrationResponse
                .builder()
                .companySize(mappedCompany.getCompanySize())
                .companyEmail(mappedCompany.getCompanyEmail())
                .companyName(mappedCompany.getCompanyName())
                .companyUniqueID(mappedCompany.getCompanyUniqueID())
                .superAdminEmail(mappedSuperAdmin.getEmail())
                .superAdminFirstName(mappedSuperAdmin.getFirstName())
                .superAdminLastName(mappedSuperAdmin.getLastName())
                .build();
    }

    private static String generateRandomNumber() {
        String part1 = String.valueOf(generateThreeDigitNumber());
        String part2 = String.valueOf(generateThreeDigitNumber());
        return part1 + "/" + part2;
    }

    private static String companyUniqueID(String name) {
        String firstThreeLetter = companyFirstThreeLetter(name);
        return firstThreeLetter + "/" + generateRandomNumber();
    }

    private static int generateThreeDigitNumber() {
        Random random = new Random();
        return random.nextInt(900) + 100;
    }

    private static String companyFirstThreeLetter(String name) {
        if (name != null && name.length() >= 3)
            return name.substring(0, 3).toUpperCase();
        else throw new RuntimeException("Invalid.........");
    }

    public static String generateToken(String companyId) {
        return JWT.create().withIssuedAt(Instant.now()).withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("companyId", companyId)
                .sign(Algorithm.HMAC512("secret".getBytes()));
    }
}
