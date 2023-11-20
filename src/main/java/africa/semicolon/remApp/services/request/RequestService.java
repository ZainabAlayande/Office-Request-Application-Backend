package africa.semicolon.remApp.services.request;

import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;

import java.util.List;

public interface RequestService {

    MakeRequestResponse makeRequest(String userId, MakeRequestForm makeRequestForm);
    List<ViewRequestResponse> viewAllRequestByUserId(String userId);
    ApiResponse<?> viewAllPendingRequest(String userId);
    ApiResponse<?> viewAllAssignedRequest(String userId);
    ApiResponse<?> viewAllDeclinedRequest(String userId);
    ApiResponse<?> viewAllCompletedRequest(String userId);

//    ApiResponse<?> viewAllCompletedRequest();
//    ApiResponse<?> viewAllPendingRequest();
//    ApiResponse<?> viewAllAssignedRequest();
//    ApiResponse<?> viewAllDeclinedRequest();
}
