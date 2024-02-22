package africa.semicolon.remApp.services.company;

import africa.semicolon.remApp.dtos.requests.CompanyRegistrationRequest;
import africa.semicolon.remApp.dtos.responses.ApiResponse;
import africa.semicolon.remApp.dtos.responses.CompanyRegistrationResponse;
import africa.semicolon.remApp.dtos.responses.EmployeeListResponse;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static africa.semicolon.remApp.services.company.CompanyUtils.buildCompanyRegistrationResponse;
import static africa.semicolon.remApp.services.company.CompanyUtils.mapEmployeeToEmployeeResponse;

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
        var savedCompany = companyRepository.save(mappedCompany);
        CompanyRegistrationResponse response = buildCompanyRegistrationResponse(mappedCompany);
        return ApiResponse.builder().message("Successful").theEnum(savedCompany.getRoles().toString()).status(true).data(response).build();
    }

    @Override
    @Transactional
    public void updateMemberCount(Company company, int increment) {
        if (company != null) {
            int newMemberCount = company.getMemberCount() + increment;
            company.setMemberCount(newMemberCount);
            companyRepository.save(company);
        }
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

    @Override
    public List<EmployeeListResponse> getCompanyEmployees(String companyId) {
        Company company = companyRepository.findByUniqueID(companyId);
        List<Employee> employees = company.getEmployee();
        List<EmployeeListResponse> response = mapEmployeeToEmployeeResponse(employees);
        return response;
    }

    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }
}
