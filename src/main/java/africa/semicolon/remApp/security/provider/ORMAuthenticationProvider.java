package africa.semicolon.remApp.security.provider;

import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.security.user.CompanyDetailsService;
import africa.semicolon.remApp.security.user.EmployeeDetailsService;
import africa.semicolon.remApp.services.company.CompanyService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@AllArgsConstructor
@Component
public class ORMAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final CompanyService companyService;

    private final EmployeeDetailsService employeeService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("auth boss");
        Authentication authenticationResult = null;
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        authenticationResult = authenticateIfAuthenticationIsACompany(email, password);
        System.out.println("auth result -> " + authenticationResult);
        if (authenticationResult == null) {
                authenticationResult = authenticateIfAuthenticationIsAnEmployee(email, password);
        }
        return authenticationResult;
    }


    private Authentication authenticateIfAuthenticationIsAnEmployee(String principal, String password) throws REMAException {
        System.out.println("Auth provider employee");
        Authentication authenticationResult;
        System.out.println("1");
        UserDetails userDetails = employeeService.loadUserByUsername(principal);
        System.out.println("2");
        if (!(userDetails == null)) {
            System.out.println("3");
            String memberUsername = userDetails.getUsername();
            String memberPassword = userDetails.getPassword();
            Collection<? extends  GrantedAuthority>  authorities = userDetails.getAuthorities();
            if (!memberUsername.isEmpty()) {
                if (passwordEncoder.matches(password, memberPassword)) {
                    authenticationResult = new UsernamePasswordAuthenticationToken(memberUsername, memberPassword, authorities);
                    return authenticationResult;
                }
            }
        }
        System.out.println("4");
        return null;
    }

    private Authentication authenticateIfAuthenticationIsACompany(String principal, String password) {
        System.out.println("Auth provider company");
        Authentication authenticationResult = null;
        CompanyDetailsService companyDetailsService = new CompanyDetailsService(companyService);
        UserDetails userDetails = companyDetailsService.loadUserByUsername(principal);
        if(userDetails != null) {
            String companyEmail = userDetails.getUsername();
            String companyPassword = userDetails.getPassword();

            if (!(companyEmail == null)){
                if(passwordEncoder.matches(password, companyPassword)){
                    authenticationResult = new UsernamePasswordAuthenticationToken(companyEmail, companyPassword);
                    return authenticationResult;
                }
            }}
        System.out.println("Auth provider company finished");
        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        if (authentication.equals(UsernamePasswordAuthenticationToken.class)) return true;
        return false;
    }
}
