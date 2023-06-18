package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.LoginRequest;
import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.requests.RegisterRequest;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.dtos.responses.LoginResponse;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
import static africa.semicolon.remApp.utils.FailedResponseUtils.EMPLOYEE_REGISTRATION_FAILED;

@Service
@AllArgsConstructor
public class remaEmployeeService implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeRegisterResponse register(RegisterRequest registerRequest) throws EmployeeRegistrationFailedException {
        BioData bioData = modelMapper.map(registerRequest, BioData.class);
        Employee employee = new Employee();
        employee.setBioData(bioData);
        Employee savedEmployee = employeeRepository.save(employee);

        if (savedEmployee.getId() == null) throw new EmployeeRegistrationFailedException(String.format(EMPLOYEE_REGISTRATION_FAILED, registerRequest.getOfficeEmailAddress()));
        return buildEmployeeResponse(savedEmployee.getId());
    }

    private static EmployeeRegisterResponse buildEmployeeResponse(Long employeeId) {
        EmployeeRegisterResponse employeeRegisterResponse = new EmployeeRegisterResponse();
        employeeRegisterResponse.setMessage(EMPLOYEE_REGISTRATION_SUCCESSFUL);
        employeeRegisterResponse.setId(employeeId);
        return employeeRegisterResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public MakeRequestResponse makeRequest(MakeRequestForm makeRequestForm) {
        return null;
    }

    @Override
    public ViewRequestResponse viewRequest() {
        return null;
    }
}
