package africa.semicolon.remApp.services.company;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.CompanyRegistrationResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeListResponse;
import africa.semicolon.remApp.enums.MemberInviteStatus;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class CompanyUtils {

    private final PasswordEncoder passwordEncoder;


    protected Company buildCompanyInformation(CompanyRegistrationRequest request) {
        Company company = new Company();
        company.setEmail(request.getEmail());
        company.setConfirmPassword(request.getConfirmPassword());
        company.setName(request.getName());
        company.setPassword(passwordEncoder.encode(request.getPassword()));
        company.setUniqueID(companyUniqueID(request.getName()));
        company.setRoles(List.of(Role.ADMIN));
        company.setTimeCreated(LocalDateTime.now());
        return company;
    }

    protected static CompanyRegistrationResponse buildCompanyRegistrationResponse(Company mappedCompany) {
        return  CompanyRegistrationResponse
                .builder()
                .companyEmail(mappedCompany.getEmail())
                .companyName(mappedCompany.getName())
                .companyUniqueID(mappedCompany.getUniqueID())
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


    public static List<EmployeeListResponse> mapEmployeeToEmployeeResponse(List<Employee> employee) {
        List<EmployeeListResponse> responseList = new ArrayList<>();
        EmployeeListResponse response = new EmployeeListResponse();
        for (Employee eachEmployee: employee) {
            response.setEmail(eachEmployee.getEmail());
            response.setInviteStatus(MemberInviteStatus.JOINED);
            response.setProfilePicture(eachEmployee.getProfilePicture());
            response.setName(eachEmployee.getFirstName() + " " + eachEmployee.getLastName());

            responseList.add(response);
        }
        return responseList;
    }

    protected static String generateToken(String companyId) {
        return JWT.create().withIssuedAt(Instant.now()).withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("companyId", companyId)
                .sign(Algorithm.HMAC512("secret".getBytes()));
    }



}
