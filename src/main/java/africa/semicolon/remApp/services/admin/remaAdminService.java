package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.dtos.requests.*;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.InvitationLinkResponse;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.models.Requests;
import africa.semicolon.remApp.repositories.AdminRepository;
import africa.semicolon.remApp.security.JwtUtil;
import africa.semicolon.remApp.services.company.CompanyService;
import africa.semicolon.remApp.services.employee.EmployeeService;
import africa.semicolon.remApp.services.notification.MailService;
import africa.semicolon.remApp.services.request.RequestService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static africa.semicolon.remApp.services.admin.AdminUtils.*;
import static africa.semicolon.remApp.utils.AppUtils.JWT_SECRET;
import static africa.semicolon.remApp.utils.EmailUtils.MAIL_TEMPLATE_LOCATION;

@Service
@AllArgsConstructor
public class remaAdminService implements AdminService{


    private AdminRepository adminRepository;
    private final RequestService requestService;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final MailService mailService;
    private final JwtUtil jwtUtil;


    @Override
    @Transactional
    public InvitationLinkResponse generateInviteLinkForMember(String token, List<String> email, Authentication authentication) {
        validateEmailAddressList(email);
        String uniqueID = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Company company = companyService.findByUniqueID(uniqueID);
        Employee employee = null;
        for (String emailAddress: email) {
            employee = new Employee();
            employee.setEmail(emailAddress);
            employee.setInviteStatus("PENDING");
            employeeService.save(employee);
        }
        String link = generateInvitationLink(uniqueID);
        EmailNotificationRequest emailNotificationRequest = buildEmailDetails(link, email, company.getName());
        mailService.sendMail(emailNotificationRequest);
        assert employee != null;
        return InvitationLinkResponse.builder().status(employee.getInviteStatus()).message("Invite successfully sent").build();
    }

    @SneakyThrows
    private EmailNotificationRequest buildEmailDetails(String link, List<String> email, String companyName) {
        Set<Recipient> recipients = new HashSet<>();
        for (String singleEmail: email) {
            String name = extractNameFromEmail(singleEmail);
            Recipient recipient = new Recipient(singleEmail, name);
            recipients.add(recipient);
        }
        Sender sender = new Sender("Office Flow", "theofficeflow@gmail.com");
        String emailContent = getEmailTemplate(link, companyName);
        return EmailNotificationRequest.builder()
                .emailSender(sender)
                .content(emailContent)
                .recipient(recipients)
                .subject("INVITATION TO JOIN OFFICE FLOW")
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
