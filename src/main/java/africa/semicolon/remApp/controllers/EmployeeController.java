package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.CompleteRegistrationRequest;
import africa.semicolon.remApp.dtos.requests.EmployeeRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.services.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RequestMapping("/api/v1/employee")
@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody EmployeeRegistrationRequest request, @RequestHeader("Authorization") String token) throws REMAException {
        System.out.println("getting her for real");
        var response = employeeService.registration(request, token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/retrieveEmployeeInformation")
    public ResponseEntity<ApiResponse<?>> retrieveEmployeeInformation(@RequestHeader("Authorization") String token) throws REMAException {
        System.out.println("getting her for real");
        var response = employeeService.retrieveEmployeeInformation();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder().message("Successfully retrieved").data(response).status(true).build());
    }

//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "api/v1/employee/complete-registration",
//            produces = {MediaType.APPLICATION_JSON_VALUE}
//    )
//    public ResponseEntity<?> completeRegistration(@RequestHeader("Authorization") String token, @RequestBody CompleteRegistrationRequest request) throws REMAException, UnsupportedEncodingException {
//        var response = employeeService.completeRegistration(token, request);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

}
