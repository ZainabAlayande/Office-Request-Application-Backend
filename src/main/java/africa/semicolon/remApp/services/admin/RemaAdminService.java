package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.dtos.requests.*;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.InvitationLinkResponse;
import africa.semicolon.remApp.dtos.responses.Response;
import africa.semicolon.remApp.enums.MemberInviteStatus;
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
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

import static africa.semicolon.remApp.services.admin.AdminUtils.*;
import static africa.semicolon.remApp.services.employee.REMAEmployeeUtils.employeeUniqueID;
import static africa.semicolon.remApp.utils.AppUtils.JWT_SECRET;

@Service
@AllArgsConstructor
public class RemaAdminService implements AdminService{

    private static final Logger logger = LoggerFactory.getLogger(RemaAdminService.class);
    private AdminRepository adminRepository;
    private final RequestService requestService;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final MailService mailService;
    private final JwtUtil jwtUtil;


    @Override
    @Transactional
    public InvitationLinkResponse<?> generateInviteLinkForMember(List<String> email) {
        String uniqueID = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        validateEmailAddressList(email, uniqueID);
        logger.info("The unique id => {}", uniqueID);
        Company company = companyService.findByUniqueID(uniqueID);
        Employee employee = null;
        List<Object> list = new ArrayList<>();

        for (String emailAddress : email) {
            employee = new Employee();
            employee.setEmail(emailAddress);
            employee.setInviteStatus(MemberInviteStatus.PENDING);
            employee.setProfilePicture("");
            employee.setUniqueId(employeeUniqueID(emailAddress));
            employeeService.saveEmployee(employee);

            Response response = Response.builder().status(employee.getInviteStatus().getName()).profilePicture("")
                    .email(employee.getEmail()).build();

            list.add(response);
            company.getEmployee().add(employee);

            String link = generateInvitationLink(uniqueID, employee.getUniqueId());
            EmailNotificationRequest emailNotificationRequest = buildEmailDetails(link, emailAddress, company.getName());
            mailService.sendMail(emailNotificationRequest);
        }

        companyService.saveCompany(company);

        if (employee != null) {
            return InvitationLinkResponse.builder().data(list).message("Invite successfully sent").companyName(company.getName())
                    .memberCount(String.valueOf(company.getMemberCount())).build();
        } else {
            return InvitationLinkResponse.builder().message("Invite failed").companyName("")
                    .memberCount(String.valueOf(company.getMemberCount())).build();
        }
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

    @SneakyThrows
    private EmailNotificationRequest buildEmailDetails(String link, String email, String companyName) {
        String name = extractNameFromEmail(email);
        Recipient recipient = new Recipient(email, name);
        Sender sender = new Sender("Office Flow", "theofficeflow@gmail.com");
        String emailContent = getEmailTemplate(link, companyName);
        return EmailNotificationRequest.builder()
                .emailSender(sender)
                .content(emailContent)
                .recipient(Set.of(recipient))
                .subject("INVITATION TO JOIN OFFICE FLOW")
                .build();
    }

    private String generateInvitationLink(String companyId,  String employeeId) {
        return "localhost:5173/employee-registration?token=" + JWT.create()
                .withClaim("companyId", companyId)
                .withClaim("employeeId", employeeId)
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
