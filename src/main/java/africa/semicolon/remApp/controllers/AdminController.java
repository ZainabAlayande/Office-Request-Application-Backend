package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.AdminRegisterRequest;
import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.services.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/admin-registration",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> adminRegistration(@RequestBody AdminRegisterRequest request)  {
        var response = adminService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
