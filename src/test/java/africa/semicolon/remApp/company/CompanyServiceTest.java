package africa.semicolon.remApp.company;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.services.company.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    private CompanyRegistrationRequest companyRegistrationRequest = new CompanyRegistrationRequest();

    @BeforeEach
    public void setUp() {
        companyRegistrationRequest.setCompanyEmail("coopera@gmail.com");
        companyRegistrationRequest.setCompanyName("Coopera");
        companyRegistrationRequest.setCompanySize("2000");
        companyRegistrationRequest.setCompanyPassword("Coopera");
        companyRegistrationRequest.setSuperAdminEmail("admin@gmail.com");
        companyRegistrationRequest.setSuperAdminLastName("Last Name");
        companyRegistrationRequest.setSuperAdminFirstName("First Name");
        companyRegistrationRequest.setSuperAdminPassword("Password");
    }

    @Test
    public void registerCompany() {
        ApiResponse<?> response = companyService.register(companyRegistrationRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Successful");
    }

    @Test
    public void validateCompanyExistThrowsException() {

    }
}
