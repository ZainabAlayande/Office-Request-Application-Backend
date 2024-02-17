package africa.semicolon.remApp.services.company;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.models.Company;

import java.util.Optional;

public interface CompanyService {

    ApiResponse<?> register(CompanyRegistrationRequest request);

    Optional<Company> findCompanyByEmail(String username);

    Company findByUniqueID(String uniqueId);
}
