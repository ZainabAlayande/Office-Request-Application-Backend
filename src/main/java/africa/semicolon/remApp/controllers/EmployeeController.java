package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.RegisterRequest;
import africa.semicolon.remApp.exceptions.remaException;
import africa.semicolon.remApp.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/v1/employee/register",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) throws remaException {
        var response = employeeService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
