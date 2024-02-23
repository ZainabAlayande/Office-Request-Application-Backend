package africa.semicolon.remApp.services.employee;

import africa.semicolon.remApp.dtos.requests.*;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.Response;
import africa.semicolon.remApp.enums.MemberInviteStatus;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import africa.semicolon.remApp.services.company.CompanyService;
import africa.semicolon.remApp.services.notification.MailService;
import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static africa.semicolon.remApp.services.employee.REMAEmployeeUtils.*;
import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
import static africa.semicolon.remApp.utils.FailedResponseUtils.EMPLOYEE_REGISTRATION_FAILED;

@Service
@AllArgsConstructor
@Slf4j
public class REMAEmployeeService implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private CompanyService companyService;
    private REMAEmployeeUtils employeeUtils;
    private final MailService mailService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ApiResponse<?> registration(EmployeeRegistrationRequest request, String token) throws REMAException {
        validateEmail(request.getEmail());
        System.out.println("token => " + token);

        Map<String, Claim> claim = jwtUtil.extractClaimsFromToken(token);
        String companyId = claim.get("companyId").asString();

        Optional<Employee> employeeOptional = Optional.ofNullable(employeeRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new REMAException("Employee not found with email: " + request.getEmail())));

        Employee foundEmployee = employeeOptional.get();
        foundEmployee.setFirstName(request.getFirstName());
        foundEmployee.setLastName(request.getLastName());
        foundEmployee.setPassword(request.getPassword());
        foundEmployee.setTimeCreated(LocalDateTime.now());
        foundEmployee.setInviteStatus(MemberInviteStatus.JOINED);
        foundEmployee.getRoles().add(Role.EMPLOYEE);

        Company company = companyService.findByUniqueID(companyId);
        company.getEmployee().add(foundEmployee);
        companyService.updateMemberCount(company, 1);

        employeeRepository.save(foundEmployee);

        EmailNotificationRequest emailNotificationRequest = employeeUtils.buildEmailRequest(request.getEmail());
        var response = mailService.sendMail(emailNotificationRequest);
        log.info("Mail service response: {}", response);

        if (foundEmployee.getId() == null) {
            throw new EmployeeRegistrationFailedException(String.format(EMPLOYEE_REGISTRATION_FAILED, request.getEmail()));
        }
        return ApiResponse.builder().message(EMPLOYEE_REGISTRATION_SUCCESSFUL).status(true).build();
    }

    @SneakyThrows
    @Override
    public List<Response> retrieveEmployeeInformation() {
        List<Response> responseList = new ArrayList<>();
        String companyId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Company company = companyService.findByUniqueID(companyId);
        if (company != null) {
            List<Employee> foundEmployee = company.getEmployee();
            for (Employee employee: foundEmployee) {
                Response response = mapEmployeeListToResponse(employee);
                responseList.add(response);
            }
        } else {
            throw new REMAException("Company not found");
        }
        return responseList;
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
        return employeeRepository.findByEmail(email).orElseThrow(() -> new REMAException("Employee not found"));
  }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
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
