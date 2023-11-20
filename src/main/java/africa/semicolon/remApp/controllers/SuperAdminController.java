package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.requests.CompleteRegistrationRequest;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.services.superAdmin.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/company-registration",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> companyRegistration(@RequestBody CompanyRegistrationRequest request)  {
        var response = superAdminService.companyRegistration(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
