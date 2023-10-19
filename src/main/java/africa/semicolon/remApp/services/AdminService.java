package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.AdminLoginRequest;
import africa.semicolon.remApp.dtos.requests.AdminRegisterRequest;
import africa.semicolon.remApp.dtos.responses.AdminLoginResponse;
import africa.semicolon.remApp.dtos.responses.AdminRegisterResponse;
import africa.semicolon.remApp.dtos.responses.PendingRequestResponse;

import java.util.List;

public interface AdminService {

    AdminRegisterResponse register(AdminRegisterRequest adminRegisterRequest);
    AdminLoginResponse login(AdminLoginRequest adminLoginRequest);

    List<PendingRequestResponse> viewPendingRequest();
    void assignedRequest();
    void declineRequest();
    void completeRequest();
}
