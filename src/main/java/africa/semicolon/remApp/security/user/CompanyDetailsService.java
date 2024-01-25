package africa.semicolon.remApp.security.user;

import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.repositories.CompanyRepository;
import africa.semicolon.remApp.services.company.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@AllArgsConstructor
public class CompanyDetailsService implements UserDetailsService {

    private final CompanyService companyService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Company> foundCompany =  companyService.findCompanyByEmail(username);
        return foundCompany.map(CompanyDetails::new).orElse(null);
    }
}
