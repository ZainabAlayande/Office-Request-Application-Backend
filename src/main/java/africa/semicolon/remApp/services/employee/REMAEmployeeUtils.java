package africa.semicolon.remApp.services.employee;

import africa.semicolon.remApp.dtos.requests.EmailNotificationRequest;
import africa.semicolon.remApp.dtos.requests.Recipient;
import africa.semicolon.remApp.dtos.requests.Sender;
import africa.semicolon.remApp.dtos.responses.CompleteRegistrationResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
import static africa.semicolon.remApp.utils.EmailUtils.*;

@AllArgsConstructor
public class REMAEmployeeUtils {

    private EmployeeRepository employeeRepository;

    private BioDataRepository bioDataRepository;
    private JwtUtil jwtUtil;


    public static CompleteRegistrationResponse buildCompleteRegistrationResponse(Employee employee, String token) {
        return CompleteRegistrationResponse.builder()
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .officeLine(employee.getOfficeLine())
                .officeLocation(employee.getOfficeLocation())
                .profilePicture(employee.getProfilePicture())
                .position(employee.getPosition())
                .token(token)
                .build();
    }

    public static EmployeeRegisterResponse buildEmployeeResponse(Long employeeId) {
        EmployeeRegisterResponse employeeRegisterResponse = new EmployeeRegisterResponse();
        employeeRegisterResponse.setMessage(EMPLOYEE_REGISTRATION_SUCCESSFUL);
        employeeRegisterResponse.setId(employeeId);
        return employeeRegisterResponse;
    }

    public EmailNotificationRequest buildEmailRequest(String email) throws REMAException {
        if (bioDataRepository.existsByOfficeEmailAddress(email)) {
            throw new REMAException("Email exist........please login");
        } else {
            BioData bioData = new BioData();
            bioData.setOfficeEmailAddress(email);
            Employee employee = new Employee();
            employee.setBioData(bioData);
            String token = jwtUtil.generateAccessToken(employee, Role.FRESH_USER);
            EmailNotificationRequest request = new EmailNotificationRequest();
            Sender sender = new Sender(APP_NAME, APP_EMAIL);
            Recipient recipient = new Recipient(email,"empty");
            request.setEmailSender(sender);
            request.setRecipient(Set.of(recipient));
            request.setSubject(ACTIVATION_LINK_VALUE);
            String template = getEmailTemplate();
            request.setContent(String.format(template, ACTIVATE_ACCOUNT_URL+"?"+token));
            return request;
        }
    }

    public static String getEmailTemplate() throws REMAException {
        try(BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(MAIL_TEMPLATE_LOCATION))) {
            return bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException exception) {
            throw new REMAException("Failed to send registration link");
        }
    }

}
//        String token = JWT.create()
//                .withIssuedAt(Instant.now())
//                .withExpiresAt(Instant.now().plusSeconds(60L))
//                .withClaim("email", email)
//                .sign(Algorithm.HMAC512("secret".getBytes()));