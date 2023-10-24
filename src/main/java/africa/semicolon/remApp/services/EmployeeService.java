package africa.semicolon.remApp.services;

import africa.semicolon.remApp.dtos.requests.LoginRequest;
import africa.semicolon.remApp.dtos.requests.MakeRequestForm;
import africa.semicolon.remApp.dtos.requests.RegisterRequest;
import africa.semicolon.remApp.dtos.responses.*;
import africa.semicolon.remApp.exceptions.EmployeeRegistrationFailedException;
import africa.semicolon.remApp.exceptions.remaException;
import africa.semicolon.remApp.models.Employee;

import java.util.Optional;

public interface EmployeeService {

    EmployeeRegisterResponse register(RegisterRequest registerRequest) throws remaException;

    Optional<Employee> findUserById(String userId);
}
