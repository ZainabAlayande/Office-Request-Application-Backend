package africa.semicolon.remApp.user;

import africa.semicolon.remApp.dtos.requests.RegisterRequest;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.exceptions.remaException;
import africa.semicolon.remApp.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    private RegisterRequest registerRequest;
    private EmployeeRegisterResponse employeeRegisterResponse;

    @BeforeEach
    public void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setOfficeEmailAddress("monetale@gmail.com");
        registerRequest.setPassword("Dami2020988");
        employeeRegisterResponse = new EmployeeRegisterResponse();
    }
    @Test
    public void employeeCanSupplyBioData_Test() throws remaException {
        employeeRegisterResponse = employeeService.register(registerRequest);
        assertThat(employeeRegisterResponse).isNotNull();
    }




}
