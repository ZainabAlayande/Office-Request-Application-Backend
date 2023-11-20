package africa.semicolon.remApp.services.superAdmin;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.CompanyRegistrationResponse;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.SuperAdmin;
import africa.semicolon.remApp.repositories.CompanyRepository;
import africa.semicolon.remApp.repositories.SuperAdminRepository;
import africa.semicolon.remApp.security.JwtUtil;
import org.modelmapper.ModelMapper;

import static africa.semicolon.remApp.services.superAdmin.SuperAdminUtils.*;

public class SuperAdminServiceImpl implements SuperAdminService {

    private CompanyRepository companyRepository;
    private SuperAdminRepository superAdminRepository;
    private JwtUtil jwtUtil;

    @Override
    public ApiResponse<?> companyRegistration(CompanyRegistrationRequest request) {
        Company mappedCompany = buildCompanyInformation(request);
        SuperAdmin mappedSuperAdmin = buildSuperAdminInformation(request);
        companyRepository.save(mappedCompany);
        superAdminRepository.save(mappedSuperAdmin);
        String token = generateToken(mappedCompany.getCompanyUniqueID());
        CompanyRegistrationResponse response = buildCompanyRegistrationResponse(mappedCompany, mappedSuperAdmin);
        response.setToken(token);
        return ApiResponse.builder().message("Successful").status(true).data(response).build();
    }

}
