package africa.semicolon.remApp.services.employee;

import africa.semicolon.remApp.dtos.requests.EmailNotificationRequest;
import africa.semicolon.remApp.dtos.requests.Recipient;
import africa.semicolon.remApp.dtos.requests.Sender;
import africa.semicolon.remApp.dtos.responses.CompleteRegistrationResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
import static africa.semicolon.remApp.utils.EmailUtils.*;

@AllArgsConstructor
public class REMAEmployeeUtils {

    private EmployeeRepository employeeRepository;

    private JwtUtil jwtUtil;

    private static final String EMAIL_REGEX_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);


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
        EmailNotificationRequest request = new EmailNotificationRequest();
        String name = extractNameFromEmail(email);
        Sender sender = new Sender(APP_NAME, APP_EMAIL);
        Recipient recipient = new Recipient(email,name);
        request.setEmailSender(sender);
        request.setRecipient(Set.of(recipient));
        request.setSubject("Registration Successful");
        String template = getEmailTemplate();
        request.setContent(String.format(template));
        return request;
    }

    public static String extractNameFromEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            return email;
        }
    }

    public static String getEmailTemplate() throws REMAException {
        try(BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(MAIL_TEMPLATE_LOCATION))) {
            return bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException exception) {
            throw new REMAException("Failed to send mail");
        }
    }

    @SneakyThrows
    protected static void validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new EmployeeRegistrationFailedException("Invalid email address");
        }

    }

}
//        String token = JWT.create()
//                .withIssuedAt(Instant.now())
//                .withExpiresAt(Instant.now().plusSeconds(60L))
//                .withClaim("email", email)
//                .sign(Algorithm.HMAC512("secret".getBytes()));