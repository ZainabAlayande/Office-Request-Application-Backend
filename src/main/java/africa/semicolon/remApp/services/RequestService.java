package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.PendingRequestResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;

import java.util.List;

public interface RequestService {

    MakeRequestResponse makeRequest(MakeRequestForm makeRequestForm);
    List<ViewRequestResponse> viewAllRequestByUserId(String userId);
    List<PendingRequestResponse> buildPendingRequest();
}
