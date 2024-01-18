package africa.semicolon.remApp.security.provider;

import africa.semicolon.remApp.security.user.CompanyDetails;
import africa.semicolon.remApp.security.user.CompanyDetailsService;
import africa.semicolon.remApp.security.user.EmployeeDetailsService;
import africa.semicolon.remApp.services.company.CompanyService;
import africa.semicolon.remApp.services.employee.EmployeeService;
import lombok.AllArgsConstructor;
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

    private final EmployeeService employeeService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Inside authentication provider");
        Authentication authenticationResult;
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        authenticationResult = authenticateIfAuthenticationIsACompany(email, password);
        System.out.println("auth result -> " + authenticationResult);
//        if (authenticationResult == null) {
//            authenticationResult = authenticateIfAuthenticationIsAMember(email, password);
//        }
        return authenticationResult;
    }

    private Authentication authenticateIfAuthenticationIsAMember(String principal, String password) {
        Authentication authenticationResult;
        EmployeeDetailsService employeeDetailsService = new EmployeeDetailsService(employeeService);
        UserDetails userDetails = employeeDetailsService.loadUserByUsername(principal);
        if (!(userDetails == null)) {
            String memberUsername = userDetails.getUsername();
            String memberPassword = userDetails.getPassword();
            if (!memberUsername.isEmpty()) {
                if (passwordEncoder.matches(password, memberPassword)) {
                    authenticationResult = new UsernamePasswordAuthenticationToken(memberUsername, memberPassword);
                    return authenticationResult;
                }
            }
        }
        return null;
    }

    private Authentication authenticateIfAuthenticationIsACompany(String principal, String password) {
        Authentication authenticationResult;
        CompanyDetailsService companyDetailsService = new CompanyDetailsService(companyService);
        UserDetails userDetails = companyDetailsService.loadUserByUsername(principal);
        System.out.println("user details -> " + userDetails.toString());
        System.out.println("user details username -> " + userDetails.getUsername());
        System.out.println("user details password -> " + userDetails.getPassword());
        if(userDetails != null) {
            System.out.println("1");
            String companyEmail = userDetails.getUsername();
            String companyPassword = userDetails.getPassword();

            System.out.println();
            System.out.println("password 1 -> " + password);
            System.out.println("password 1 -> " + companyPassword);

            if (passwordEncoder instanceof BCryptPasswordEncoder) {
                System.out.println("Using BCryptPasswordEncoder");
            } else {
                System.out.println("Using a different PasswordEncoder");
            }


            System.out.println();
            System.out.println("2");
            if (!(companyEmail == null)){
                System.out.println("3");
                if(passwordEncoder.matches(password, companyPassword)){
                    System.out.println("4");
                    authenticationResult = new UsernamePasswordAuthenticationToken(companyEmail, companyPassword);
                    System.out.println("5");
                    return authenticationResult;
                }
            }}
        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("Inside authentication provider support");
        if (authentication.equals(UsernamePasswordAuthenticationToken.class)) return true;
        return false;
    }
}
