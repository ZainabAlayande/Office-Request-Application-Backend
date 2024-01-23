package africa.semicolon.remApp.services.employee;

import africa.semicolon.remApp.dtos.requests.*;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.CompleteRegistrationResponse;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import africa.semicolon.remApp.services.notification.MailService;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static africa.semicolon.remApp.services.employee.REMAEmployeeUtils.*;
import static africa.semicolon.remApp.services.request.RequestServiceUtils.buildEmployee;
import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
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
    public ApiResponse<?> registration(EmployeeRegistrationRequest request) throws REMAException {
        validateEmail(request.getEmail());
        Employee employee = Employee.builder().lastName(request.getLastName()).firstName(request.getFirstName())
                .email(request.getEmail()).password(request.getPassword()).roles(List.of(Role.EMPLOYEE)).timeCreated(LocalDateTime.now()).build();

        Employee savedEmployee = employeeRepository.save(employee);
//        EmailNotificationRequest emailNotificationRequest = employeeUtils.buildEmailRequest(request.getEmail());
//        var response = mailService.sendMail(emailNotificationRequest);
//        log.info("response-->{}", response);

        if (savedEmployee.getId() == null)
            throw new EmployeeRegistrationFailedException(String.format(EMPLOYEE_REGISTRATION_FAILED, request.getEmail()));
        return ApiResponse.builder().message(EMPLOYEE_REGISTRATION_SUCCESSFUL).status(true).build();
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
        return employeeRepository.findByEmail(email).orElseThrow(() -> new REMAException("Employee not found"));
    }



//    @Override
//    @Transactional
//    public ApiResponse<?> completeRegistration(String token, CompleteRegistrationRequest request) throws REMAException, UnsupportedEncodingException {
//        System.out.println("In the endpoint 1");
//        String spilitedToken = token.split(" ")[1];
//        System.out.println("In the endpoint 1");
//        DecodedJWT decodedJWT = jwtUtil.verifyToken(spilitedToken);
//        String email = decodedJWT.getClaim("email").asString();
//        Optional<Employee> employee = employeeRepository.findByEmail(email);
//        Employee foundEmployee = employee.orElseThrow(() -> new REMAException("Invalid................."));
//        Employee mappedEmployee = buildEmployee(foundEmployee, request);
//        String employeeToken = jwtUtil.generateAccessTokenAfterLogin(mappedEmployee, Role.EMPLOYEE);
//        employeeRepository.save(mappedEmployee);
//        CompleteRegistrationResponse response = buildCompleteRegistrationResponse(mappedEmployee, employeeToken);
//        return ApiResponse.builder().message("Registration Complete").status(true).data(response).build();
//    }


}
