package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.CompleteRegistrationRequest;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.services.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/employee/register",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> register(@RequestParam("email") String email) throws REMAException {
        var response = employeeService.registration(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/employee/complete-registration",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> completeRegistration(@RequestHeader("Authorization") String token, @RequestBody CompleteRegistrationRequest request) throws REMAException, UnsupportedEncodingException {
        var response = employeeService.completeRegistration(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
