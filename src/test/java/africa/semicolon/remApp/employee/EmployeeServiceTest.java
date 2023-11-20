package africa.semicolon.remApp.employee;

import africa.semicolon.remApp.dtos.requests.CompleteRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.services.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;



    @Test
    public void employeeCanSupplyBioData_Test() throws REMAException {
        ApiResponse<?> response = employeeService.registration("zainabalayande01@gmail.com");
        assertThat(response.getMessage()).isEqualTo("Email sent......");
    }





}
