package africa.semicolon.remApp.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ORMAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticationResult = null;
        if (authenticationProvider.supports(authentication.getClass())) {
            if (authenticationProvider.supports(authentication.getClass())) {
                authenticationResult = authenticationProvider.authenticate(authentication);
                return authenticationResult;
            } else throw new BadCredentialsException("Failed");
        }
        return authenticationResult;
    }
}
