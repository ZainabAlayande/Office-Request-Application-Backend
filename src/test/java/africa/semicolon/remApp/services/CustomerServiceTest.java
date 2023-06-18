package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.requests.RegisterRequest;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.remApp.utils.AppUtils.ONE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private EmployeeService employeeService;

    private RegisterRequest registerRequest;
    private EmployeeRegisterResponse employeeRegisterResponse;
    private MakeRequestForm makeRequestForm;

    @BeforeEach
    public void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setOfficeEmailAddress("zainabalayande64@gmail.com");
        registerRequest.setPassword("password");
        employeeRegisterResponse = new EmployeeRegisterResponse();
        makeRequestForm = new MakeRequestForm();
    }
    @Test
    public void testThatEmployeeCanRegister() throws EmployeeRegistrationFailedException {
        employeeRegisterResponse = employeeService.register(registerRequest);
        assertThat(employeeRegisterResponse).isNotNull();
        assertThat(employeeRegisterResponse.getId()).isEqualTo(ONE);
    }

    @Test
    public void testThatEmployeeCanMakeRequest() {
        employeeService.makeRequest(makeRequestForm);


    }

    @Test
    public void testThatEmployeeCanViewAllRequest() {
        employeeService.viewRequest();

    }


}
