package africa.semicolon.remApp.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ORMAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;

    public ORMAuthenticationManager(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticationResult;
        if (authenticationProvider.supports(authentication.getClass())) {
            authenticationResult = authenticationProvider.authenticate(authentication);
            return authenticationResult;
        } else throw new BadCredentialsException("Failed");
    }
}
