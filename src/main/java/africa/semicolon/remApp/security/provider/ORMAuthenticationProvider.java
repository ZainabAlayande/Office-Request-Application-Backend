package africa.semicolon.remApp.security.provider;

import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.security.user.CompanyDetails;
import africa.semicolon.remApp.security.user.CompanyDetailsService;
import africa.semicolon.remApp.security.user.EmployeeDetailsService;
import africa.semicolon.remApp.services.company.CompanyService;
import africa.semicolon.remApp.services.employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        System.out.println("authentication res -> " + authenticationResult);
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        System.out.println("auth object -> " + authentication);
        System.out.println("email -> " + email);
        System.out.println("password -> " + password);
        authenticationResult = authenticateIfAuthenticationIsACompany(email, password);
        System.out.println("auth result -> " + authenticationResult);
        if (authenticationResult == null) {
            System.out.println("more description");
                authenticationResult = authenticateIfAuthenticationIsAMember(email, password);
        }
        return authenticationResult;
    }


    private Authentication authenticateIfAuthenticationIsAMember(String principal, String password) throws REMAException {
        Authentication authenticationResult;
        UserDetails userDetails = employeeService.loadUserByUsername(principal);
        if (!(userDetails == null)) {
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
        return null;
    }

    private Authentication authenticateIfAuthenticationIsACompany(String principal, String password) {
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
        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        if (authentication.equals(UsernamePasswordAuthenticationToken.class)) return true;
        return false;
    }
}
