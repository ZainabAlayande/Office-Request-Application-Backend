package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.LoginRequest;
import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.requests.RegisterRequest;
import africa.semicolon.remApp.dtos.responses.EmployeeRegisterResponse;
import africa.semicolon.remApp.dtos.responses.LoginResponse;
import africa.semicolon.remApp.dtos.responses.MakeRequestResponse;
import africa.semicolon.remApp.dtos.responses.ViewRequestResponse;

public class remaEmployeeService implements EmployeeService{

    @Override
    public EmployeeRegisterResponse register(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public MakeRequestResponse makeRequest(MakeRequestForm makeRequestForm) {
        return null;
    }

    @Override
    public ViewRequestResponse viewRequest() {
        return null;
    }
}
