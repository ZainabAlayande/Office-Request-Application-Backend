package africa.semicolon.remApp.employee;

import africa.semicolon.remApp.dtos.requests.CompleteRegistrationRequest;
import africa.semicolon.remApp.dtos.requests.EmployeeRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.services.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.remApp.utils.AcceptedResponseUtils.EMPLOYEE_REGISTRATION_SUCCESSFUL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    private EmployeeRegistrationRequest employeeRegistrationRequest;

    @BeforeEach
    public void setUp() {
        employeeRegistrationRequest = new EmployeeRegistrationRequest();
        employeeRegistrationRequest.setFirstName("Cephars");
        employeeRegistrationRequest.setLastName("Iyiola");
        employeeRegistrationRequest.setEmail("alayandezainab@gmail.com");
        employeeRegistrationRequest.setPassword("zainab");
    }


    @Test
    public void employeeCanRegister_Test() throws REMAException {
        ApiResponse<?> response = employeeService.registration(employeeRegistrationRequest, "");
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo(EMPLOYEE_REGISTRATION_SUCCESSFUL);
    }







}
