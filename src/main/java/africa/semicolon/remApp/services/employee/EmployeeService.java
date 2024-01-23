package africa.semicolon.remApp.services.employee;

import africa.semicolon.remApp.dtos.requests.CompleteRegistrationRequest;
import africa.semicolon.remApp.dtos.requests.EmployeeRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.*;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Employee;
import io.micrometer.common.lang.NonNull;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface EmployeeService {

    ApiResponse<?> registration(EmployeeRegistrationRequest request) throws REMAException;

    Optional<Employee> findUserById(String userId);

    Employee findEmployeeByEmail(String username) throws REMAException;

}
