package africa.semicolon.remApp.security.filter;

import africa.semicolon.remApp.dtos.requests.LoginRequest;
import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static africa.semicolon.remApp.utils.FailedResponseUtils.BADCREDENTIALSEXCEPTION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class ORMAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final BioDataRepository bioDataRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;
    private String email;
    private String password;

    public ORMAuthenticationFilter (ObjectMapper objectMapper, AuthenticationManager authenticationManager, BioDataRepository bioDataRepository, EmployeeRepository employeeRepository, JwtUtil jwtUtil) {
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.bioDataRepository = bioDataRepository;
        this.employeeRepository = employeeRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginRequest loginRequest = null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            email = loginRequest.getEmail();
            password = loginRequest.getPassword();
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            log.info("user authenticated");
            return authenticationResult;
        } catch (IOException e) {
            throw new BadCredentialsException(BADCREDENTIALSEXCEPTION);
        }
    }

    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain filterChain, Authentication authenticationResult) throws IOException {

        Optional<BioData> bioData = bioDataRepository.findByOfficeEmailAddress(email);
        BioData foundBioData = bioData.get();
        Optional<Employee> employee = employeeRepository.findByBioData(foundBioData);
        Employee foundEmployee = employee.get();

        String accessToken = jwtUtil.generateAccessToken(foundEmployee, Role.EMPLOYEE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(Map.of("access token", accessToken)));
    }

}
