package africa.semicolon.remApp.user;

import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.services.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MakeRequestTest {

    @Autowired
    private RequestService requestService;
    private MakeRequestForm makeRequestForm;
    private MakeRequestForm makeRequestFormTwo;
    private MakeRequestResponse makeRequestResponse;
    private MakeRequestResponse makeRequestResponseTwo;

    @BeforeEach
    public void setUp() {
        makeRequestForm = new MakeRequestForm();
        makeRequestFormTwo = new MakeRequestForm();
        makeRequestFormTwo.setTitle("Computer");
        makeRequestFormTwo.setDescription("The computer you brought is bad");
        makeRequestFormTwo.setRequesterName("Oseni Chike");
        makeRequestForm.setOfficeEmailAddress("osenichike@gmail.com");
        makeRequestResponseTwo = new MakeRequestResponse();

        makeRequestResponse = new MakeRequestResponse();
        makeRequestForm.setTitle("Mac Book");
        makeRequestForm.setDescription("My intern needs mac book");
        makeRequestForm.setRequesterName("Zainab Blessed");
        makeRequestForm.setOfficeEmailAddress("emailAddress@email.com");
    }
    @Test
    public void testThatEmployeeCanMakeRequest() {
       makeRequestResponse = requestService.makeRequest(makeRequestForm);
       makeRequestResponseTwo = requestService.makeRequest(makeRequestFormTwo);
       assertThat(makeRequestResponse).isNotNull();
       assertThat(makeRequestResponseTwo).isNotNull();
    }

    @Test
    public void testThatEmployeeCanViewAllRequest() {
        var response = requestService.viewAllRequestByUserId("");

    }
}
