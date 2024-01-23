package africa.semicolon.remApp.admin;

import africa.semicolon.remApp.dtos.responses.InvitationLinkResponse;
import africa.semicolon.remApp.services.admin.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void generateInviteLinkForSingleEmployeeWithEmail() {
        List<String> recipientEmails = List.of("zainabalayande01@gmail");
        InvitationLinkResponse response = adminService.generateInviteLinkForMember(recipientEmails);
        assertNotNull(response);
        assertThat(response.getMessage()).isEqualTo("Invite successfully sent");
    }

    @Test
    public void generateInviteLinkForListOfEmployeesWithEmail() {
        List<String> recipientEmails = List.of("alayandezainab64@gmail.com, oaalayande@gmail.com, alaabdulmalik@gmail.com");
        InvitationLinkResponse response = adminService.generateInviteLinkForMember(recipientEmails);
        assertNotNull(response);
        assertThat(response.getMessage()).isEqualTo("Invite successfully sent");
    }

    @Test
    public void validateEmployeeEmailForInvitationAlreadyExist() {
        List<String> recipientEmails = List.of("alayandezainab64@gmail.com, oaalayande@gmail.com, alaabdulmalik@gmail.com");
        InvitationLinkResponse response = adminService.generateInviteLinkForMember(recipientEmails);

    }



    @Test
    public void viewAllPendingRequest() {

    }

    @Test
    public void viewAllApprovedRequest() {

    }

    @Test
    public void viewAllAssignedRequest() {

    }

    @Test
    public void viewAllDeclinedRequest() {

    }

    @Test
    public void viewSinglePendingRequestByRequestId() {

    }

    @Test
    public void viewSingleAssignedRequestByRequestId() {

    }

    @Test
    public void viewSingleDeclinedRequestByRequestId() {

    }

    @Test
    public void viewSingleApprovedRequestByRequestId() {

    }

    @Test
    public void getCountOfPendingRequest() {

    }

    @Test
    public void getCountOfAssignedRequest() {

    }

    @Test
    public void getCountOfApprovedRequest() {

    }

    @Test
    public void getCountOfDeclinedRequest() {

    }
}
