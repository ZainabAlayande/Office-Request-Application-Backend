package africa.semicolon.remApp.services.employee;

import africa.semicolon.remApp.dtos.requests.EmployeeRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.*;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    ApiResponse<?> registration(EmployeeRegistrationRequest request, String token) throws REMAException;

    List<Response> retrieveEmployeeInformation();

    Optional<Employee> findUserById(String userId);

    Employee findEmployeeByEmail(String username) throws REMAException;

    void saveEmployee(Employee employee);

}
