package africa.semicolon.remApp.services.request;

import africa.semicolon.remApp.dtos.requests.CompleteRegistrationRequest;
import africa.semicolon.remApp.models.Employee;

import java.time.LocalDateTime;

public class RequestServiceUtils {

    public static Employee buildEmployee(Employee foundEmployee, CompleteRegistrationRequest request) {
        foundEmployee.setPassword(request.getPassword());
        foundEmployee.setFirstName(request.getFirstName());
        foundEmployee.setLastName(request.getLastName());
        foundEmployee.setOfficeLine(request.getOfflineLine());
        foundEmployee.setOfficeLocation(request.getOfficeLocation());
        foundEmployee.setProfilePicture(request.getProfilePicture());
        foundEmployee.setTimeCreated(LocalDateTime.now());
        return foundEmployee;
    }

}
