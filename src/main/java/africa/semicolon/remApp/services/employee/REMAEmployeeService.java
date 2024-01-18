package africa.semicolon.remApp.services.employee;

import africa.semicolon.remApp.dtos.requests.*;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.CompleteRegistrationResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import africa.semicolon.remApp.services.notification.MailService;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static africa.semicolon.remApp.services.employee.REMAEmployeeUtils.*;
import static africa.semicolon.remApp.services.request.RequestServiceUtils.buildEmployee;
import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
import static africa.semicolon.remApp.utils.EmailUtils.*;
import static africa.semicolon.remApp.utils.FailedResponseUtils.EMPLOYEE_REGISTRATION_FAILED;

@Service
@AllArgsConstructor
@Slf4j
public class REMAEmployeeService implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final BioDataRepository bioDataRepository;
    private REMAEmployeeUtils employeeUtils;
    private final MailService mailService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public ApiResponse<?> registration(@NonNull String email) throws REMAException {
        EmailNotificationRequest emailNotificationRequest = employeeUtils.buildEmailRequest(email);
        var response = mailService.sendMail(emailNotificationRequest);
        log.info("response-->{}", response);

        BioData bioData = new BioData();
        bioData.setOfficeEmailAddress(email);
        bioDataRepository.save(bioData);
        Employee employee = new Employee();
        employee.setBioData(bioData);
        Employee savedEmployee = employeeRepository.save(employee);

        if (savedEmployee.getId() == null)
            throw new EmployeeRegistrationFailedException(String.format(EMPLOYEE_REGISTRATION_FAILED, email));
        return ApiResponse.builder().message("Email sent......").status(true).build();
    }

    @Override
    @Transactional
    public ApiResponse<?> completeRegistration(String token, CompleteRegistrationRequest request) throws REMAException, UnsupportedEncodingException {
        System.out.println("In the endpoint 1");
        String spilitedToken = token.split(" ")[1];
        System.out.println("In the endpoint 1");
        DecodedJWT decodedJWT = jwtUtil.verifyToken(spilitedToken);
        String email = decodedJWT.getClaim("email").asString();
        Optional<BioData> bioData = bioDataRepository.findByOfficeEmailAddress(email);
        BioData foundBioData = bioData.orElseThrow(() -> new REMAException("Invalid................."));
        Optional<Employee> employee = employeeRepository.findByBioData(foundBioData);
        Employee foundEmployee = employee.orElseThrow(() -> new REMAException("Invalid................."));
        Employee mappedEmployee = buildEmployee(foundEmployee, request);
        String employeeToken = jwtUtil.generateAccessToken(mappedEmployee, Role.EMPLOYEE);
        employeeRepository.save(mappedEmployee);
        CompleteRegistrationResponse response = buildCompleteRegistrationResponse(mappedEmployee, employeeToken);
        return ApiResponse.builder().message("Registration Complete").status(true).data(response).build();
    }


    @Override
    public Optional<Employee> findUserById(String userId) {
        if (employeeRepository.existsById(Long.valueOf(userId))) {
            Optional<Employee> employee = employeeRepository
                    .findById(Long.valueOf(userId));
            return employee;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Employee findEmployeeByEmail(String email) throws REMAException {
        BioData bioData = new BioData();
        bioData.setOfficeEmailAddress(email);
        return employeeRepository.findByBioData(bioData).orElseThrow(() -> new REMAException("Employee not found"));
    }


}
