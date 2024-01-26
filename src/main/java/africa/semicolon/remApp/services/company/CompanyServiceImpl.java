package africa.semicolon.remApp.services.company;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.CompanyRegistrationResponse;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.SuperAdmin;
import africa.semicolon.remApp.repositories.CompanyRepository;
import africa.semicolon.remApp.repositories.SuperAdminRepository;
import africa.semicolon.remApp.services.superAdmin.SuperAdminUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.remApp.services.company.CompanyUtils.buildCompanyRegistrationResponse;
import static africa.semicolon.remApp.services.company.CompanyUtils.generateToken;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private CompanyRepository companyRepository;

    private CompanyUtils companyUtils;

    @Override
    public ApiResponse<?> register(CompanyRegistrationRequest request) {
        Company mappedCompany = companyUtils.buildCompanyInformation(request);
        companyRepository.save(mappedCompany);
        String token = generateToken(mappedCompany.getUniqueID());
        CompanyRegistrationResponse response = buildCompanyRegistrationResponse(mappedCompany);
        response.setToken(token);
        return ApiResponse.builder().message("Successful").status(true).data(response).build();
    }

    @Override
    public Optional<Company> findCompanyByEmail(String username) {
        return companyRepository.findByEmail(username);
    }
}
