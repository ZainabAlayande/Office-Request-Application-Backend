package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.AdminRegisterRequest;
import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.services.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    private Authentication authentication;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/generate-link",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> generateLink(@RequestBody List<String> email)  {
        var response = adminService.generateInviteLinkForMember(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/request/pending",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> viewAllPendingRequestByCompanyId()  {
        String companyId = SecurityContextHolder.getContext().getAuthentication().toString();
        var response = adminService.viewAllPendingRequestByCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/request/approved",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> viewAllApprovedRequestByCompanyId()  {
        String companyId = SecurityContextHolder.getContext().getAuthentication().toString();
        var response = adminService.viewAllApprovedRequestByCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/request/declined",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> viewAllDeclinedRequestByCompanyId()  {
        String companyId = SecurityContextHolder.getContext().getAuthentication().toString();
        var response = adminService.viewAllDeclinedRequestByCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "api/v1/request/assigned",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> viewAllAssignedRequestByCompanyId()  {
        String companyId = SecurityContextHolder.getContext().getAuthentication().toString();
        var response = adminService.viewAllAssignedRequestByCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
