package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.dtos.requests.*;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.InvitationLinkResponse;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.models.RequestStatus;
import africa.semicolon.remApp.models.Requests;
import africa.semicolon.remApp.repositories.AdminRepository;
import africa.semicolon.remApp.services.notification.MailService;
import africa.semicolon.remApp.services.request.RequestService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static africa.semicolon.remApp.services.admin.AdminUtils.validateEmailAddress;
import static africa.semicolon.remApp.utils.AppUtils.JWT_SECRET;

@Service
@AllArgsConstructor
public class remaAdminService implements AdminService{

    private AdminRepository adminRepository;
    private final RequestService requestService;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    /*
    *  Generating invite link means you can enter the person email in a box on the frontend and send mail to the person
    *  The email Address is being stored to avoid asking user for their email again
    *  The mail would carry a token containing the companyId
    * */
    @Override
    public InvitationLinkResponse generateInviteLinkForMember(List<String> email) {
        validateEmailAddress(email);
        for (String emailAddress: email) {
            Employee employee = new Employee();
            employee.setEmail(emailAddress);
            employee = new Employee();
        }
        String companyId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String link = generateInvitationLink(companyId);
        EmailNotificationRequest emailNotificationRequest = buildEmailDetails(link, email);
        mailService.sendMail(emailNotificationRequest);
        return InvitationLinkResponse.builder().message("Invite successfully sent").build();
    }

    private EmailNotificationRequest buildEmailDetails(String link, List<String> email) {
        Recipient recipient = new Recipient(email.toString(), "");
        Sender sender = new Sender("ORM", "");
        return EmailNotificationRequest.builder()
                .emailSender(sender)
                .content(link)
                .recipient(Set.of(recipient))
                .subject("INVITATION TO ORM")
                .build();
    }

    private String generateInvitationLink(String companyId) {
        return "localhost:5173/employee-registration?token=" + JWT.create()
                .withClaim("companyId", companyId)
                .withExpiresAt(Instant.now().plusSeconds(864000L))
                .sign(Algorithm.HMAC512(JWT_SECRET.getBytes()));
    }


    @Override
    public ApiResponse<?> viewAllPendingRequestByCompanyId(String companyId) {
        List<Requests> pendingRequest = requestService.findAllPendingRequestByCompanyId(companyId);
        return ApiResponse.builder().status(true).data(pendingRequest).message("Successful").build();
    }

    @Override
    public ApiResponse<?> viewAllAssignedRequestByCompanyId(String companyId) {
        List<Requests> assignedRequest = requestService.findAllAssignedRequestByCompanyId(companyId);
        return ApiResponse.builder().status(true).data(assignedRequest).message("Successful").build();
    }

    @Override
    public ApiResponse<?> viewAllApprovedRequestByCompanyId(String companyId) {
        List<Requests> approvedRequest = requestService.findAllAprrovedRequestByCompanyId(companyId);
        return ApiResponse.builder().status(true).data(approvedRequest).message("Successful").build();
    }

    @Override
    public ApiResponse<?> viewAllDeclinedRequestByCompanyId(String companyId) {
        List<Requests> declinedRequest = requestService.findAllDeclinedRequestByCompanyId(companyId);
        return ApiResponse.builder().status(true).data(declinedRequest).message("Successful").build();
    }


    @Override
    public Request viewPendingRequest(String requestId) {
        return null;
    }



    @Override
    public Request viewAssignedRequest(String requestId) {
        return null;
    }



    @Override
    public Request viewApprovedRequest(String requestId) {
        return null;
    }


    @Override
    public Request viewDeclinedRequest(String requestId) {
        return null;
    }

}
