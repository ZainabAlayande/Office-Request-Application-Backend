package africa.semicolon.remApp.services.company;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeListResponse;
import africa.semicolon.remApp.models.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    ApiResponse<?> register(CompanyRegistrationRequest request);

    Optional<Company> findCompanyByEmail(String username);

    Company findByUniqueID(String uniqueId);
    List<EmployeeListResponse> getCompanyEmployees(String companyId);

    void saveCompany(Company company);

    void updateMemberCount(Company company, int i);

}
