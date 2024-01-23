package africa.semicolon.remApp.services.request;

import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;
import africa.semicolon.remApp.models.Requests;

import java.util.List;

public interface RequestService {

    MakeRequestResponse makeRequest(String userId, MakeRequestForm makeRequestForm);

    List<Requests> findAllPendingRequestByCompanyId(String companyId);

    List<Requests> findAllAssignedRequestByCompanyId(String companyId);

    List<Requests> findAllAprrovedRequestByCompanyId(String companyId);

    List<Requests> findAllDeclinedRequestByCompanyId(String companyId);

//    ApiResponse<?> getAllRequestCountByCompanyId(String companyId);


























    List<ViewRequestResponse> viewAllRequestByUserId(String userId);
    ApiResponse<?> viewAllPendingRequest(String userId);
    ApiResponse<?> viewAllAssignedRequest(String userId);
    ApiResponse<?> viewAllDeclinedRequest(String userId);
    ApiResponse<?> viewAllCompletedRequest(String userId);

}
