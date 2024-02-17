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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static africa.semicolon.remApp.services.company.CompanyUtils.buildCompanyRegistrationResponse;
import static africa.semicolon.remApp.services.company.CompanyUtils.generateToken;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private CompanyRepository companyRepository;

    private CompanyUtils companyUtils;

    @Override
    @Transactional
    public ApiResponse<?> register(CompanyRegistrationRequest request) {
        Company mappedCompany = companyUtils.buildCompanyInformation(request);
        System.out.println("Mapped Company => " + mappedCompany);
        // add mail service
        var anything = companyRepository.save(mappedCompany);
        CompanyRegistrationResponse response = buildCompanyRegistrationResponse(mappedCompany);
        return ApiResponse.builder().message("Successful").theEnum(anything.getRoles().toString()).status(true).data(response).build();
    }

    @Override
    public Optional<Company> findCompanyByEmail(String username) {
        return companyRepository.findByEmail(username);
    }

    @Override
    public Company findByUniqueID(String uniqueId) {
        String replaceId = uniqueId.replaceAll("\"", "");
        System.out.println("Uni => " + replaceId);
        return companyRepository.findByUniqueID(replaceId);
    }
}
