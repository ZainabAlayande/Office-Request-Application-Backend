package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.*;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.dtos.responses.LoginResponse;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import africa.semicolon.remApp.exceptions.remaException;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.services.notification.MailService;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
import static africa.semicolon.remApp.utils.EmailUtils.*;
import static africa.semicolon.remApp.utils.FailedResponseUtils.EMPLOYEE_REGISTRATION_FAILED;

@Service
@AllArgsConstructor
@Slf4j
public class remaEmployeeService implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final MailService mailService;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeRegisterResponse register(RegisterRequest registerRequest) throws remaException {
        log.info("hello");
        BioData bioData = modelMapper.map(registerRequest, BioData.class);
        Employee employee = new Employee();
        employee.setBioData(bioData);
        Employee savedEmployee = employeeRepository.save(employee);
        EmailNotificationRequest emailNotificationRequest = buildEmailRequest(savedEmployee);
        var response = mailService.sendMail(emailNotificationRequest);
        log.info("response-->{}", response);

        if (savedEmployee.getId() == null) throw new EmployeeRegistrationFailedException(String.format(EMPLOYEE_REGISTRATION_FAILED, registerRequest.getOfficeEmailAddress()));
        return buildEmployeeResponse(savedEmployee.getId());
    }

    private static EmployeeRegisterResponse buildEmployeeResponse(Long employeeId) {
        EmployeeRegisterResponse employeeRegisterResponse = new EmployeeRegisterResponse();
        employeeRegisterResponse.setMessage(EMPLOYEE_REGISTRATION_SUCCESSFUL);
        employeeRegisterResponse.setId(employeeId);
        return employeeRegisterResponse;
    }

    private EmailNotificationRequest buildEmailRequest(Employee employee) throws remaException {
        System.out.println("employee -> " + employee.toString());
        System.out.println("build email request");
        String token = JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60L))
                .sign(Algorithm.HMAC512("secret".getBytes()));

        EmailNotificationRequest request = new EmailNotificationRequest();
        Sender sender = new Sender(APP_NAME, APP_EMAIL);
        System.out.println("2");
        Recipient recipient = new Recipient("alayandezainab64@gmail.com","Lateef Zainab");
        System.out.println("3");
        request.setEmailSender(sender);
        request.setRecipient(Set.of(recipient));
        request.setSubject(ACTIVATION_LINK_VALUE);
        String template = getEmailTemplate();
        request.setContent(String.format(template, ACTIVATE_ACCOUNT_URL+"?"+token));
        return request;
    }

    private String getEmailTemplate() throws remaException {
        try(BufferedReader bufferedReader =
                new BufferedReader(new FileReader(MAIL_TEMPLATE_LOCATION))) {
            return bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException exception) {
            throw new remaException("Failed to send registration link");
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }




}
