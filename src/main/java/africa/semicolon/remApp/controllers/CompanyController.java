package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.services.company.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/company")
@RestController
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<?> companyRegistration(@RequestBody CompanyRegistrationRequest request) {
        var response = companyService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/getCompanyMembers")
    public ResponseEntity<?> getCompanyMembers()  {
        String companyId = SecurityContextHolder.getContext().getAuthentication().toString();
        var response = companyService.getCompanyEmployees(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
