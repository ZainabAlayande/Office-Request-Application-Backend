package africa.semicolon.remApp.controllers;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.services.company.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/")
@RestController
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<?> companyRegistration(@RequestBody CompanyRegistrationRequest request) {
        var response = companyService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
