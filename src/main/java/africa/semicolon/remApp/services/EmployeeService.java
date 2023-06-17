package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.LoginRequest;
import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.requests.RegisterRequest;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.dtos.responses.LoginResponse;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;

public interface EmployeeService {

    EmployeeRegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    MakeRequestResponse makeRequest(MakeRequestForm makeRequestForm);
    ViewRequestResponse viewRequest();
}
