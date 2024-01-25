package africa.semicolon.remApp.security.filter;

import africa.semicolon.remApp.dtos.requests.LoginRequest;
import africa.semicolon.remApp.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static africa.semicolon.remApp.utils.FailedResponseUtils.BADCREDENTIALSEXCEPTION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class ORMAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private String email;
    private String password;

    @Autowired
    public ORMAuthenticationFilter (ObjectMapper objectMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("hi");
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            email = loginRequest.getEmail();
            password = loginRequest.getPassword();
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            log.info("employee authenticated");
            return authenticationResult;
        } catch (IOException e) {
            throw new BadCredentialsException(BADCREDENTIALSEXCEPTION);
        }
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String token = jwtUtil.generateAccessTokenAfterLogin(email);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(map));
    }



}
