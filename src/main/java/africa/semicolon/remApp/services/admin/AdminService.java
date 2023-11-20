package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.dtos.requests.AdminLoginRequest;
import africa.semicolon.remApp.dtos.requests.AdminRegisterRequest;
import africa.semicolon.remApp.dtos.responses.AdminLoginResponse;
import africa.semicolon.remApp.dtos.responses.AdminRegisterResponse;
import africa.semicolon.remApp.dtos.responses.ApiResponse;

import java.util.List;

public interface AdminService {

    AdminRegisterResponse register(AdminRegisterRequest adminRegisterRequest);

    ApiResponse<?> viewPendingRequest();
    void assignedRequest();
    void declineRequest();
    void completeRequest();
}
