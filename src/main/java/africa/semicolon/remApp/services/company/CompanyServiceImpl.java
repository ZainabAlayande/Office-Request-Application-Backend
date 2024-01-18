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
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.remApp.services.superAdmin.SuperAdminUtils.*;
import static africa.semicolon.remApp.services.superAdmin.SuperAdminUtils.buildCompanyRegistrationResponse;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private CompanyRepository companyRepository;
    private SuperAdminRepository superAdminRepository;
    private SuperAdminUtils superAdminUtils;

    @Override
    public ApiResponse<?> register(CompanyRegistrationRequest request) {
        Company mappedCompany = superAdminUtils.buildCompanyInformation(request);
        SuperAdmin mappedSuperAdmin = superAdminUtils.buildSuperAdminInformation(request, mappedCompany.getCompanyUniqueID());
        companyRepository.save(mappedCompany);
        superAdminRepository.save(mappedSuperAdmin);
        String token = generateToken(mappedCompany.getCompanyUniqueID());
        CompanyRegistrationResponse response = buildCompanyRegistrationResponse(mappedCompany, mappedSuperAdmin);
        response.setToken(token);
        return ApiResponse.builder().message("Successful").status(true).data(response).build();
    }

    @Override
    public Optional<Company> findCompanyByEmail(String username) {
        return companyRepository.findByCompanyEmail(username);
    }
}
