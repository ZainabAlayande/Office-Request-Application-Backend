package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.dtos.requests.Request;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.InvitationLinkResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AdminService {

    InvitationLinkResponse generateInviteLinkForMember(String token, List<String> email, Authentication authentication);

    ApiResponse<?> viewAllPendingRequestByCompanyId(String companyId);

    ApiResponse<?> viewAllAssignedRequestByCompanyId(String companyId);

    ApiResponse<?> viewAllApprovedRequestByCompanyId(String companyId);

    ApiResponse<?> viewAllDeclinedRequestByCompanyId(String companyId);












    Request viewPendingRequest(String requestId);

    Request viewAssignedRequest(String requestId);

    Request viewApprovedRequest(String requestId);

    Request viewDeclinedRequest(String requestId);

    }
