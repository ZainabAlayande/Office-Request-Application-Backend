package africa.semicolon.remApp.security.provider;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ORMAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticationResult;
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String userEmail = userDetails.getUsername();
        String userPassword = userDetails.getPassword();
        var authorities = userDetails.getAuthorities();
        if (passwordEncoder.matches(password, userPassword)) {
            authenticationResult = new UsernamePasswordAuthenticationToken(userEmail, userPassword, authorities);
            return authenticationResult;
        }
        throw new BadCredentialsException("False Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
