package africa.semicolon.remApp.security;

import africa.semicolon.remApp.enums.Role;
import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.filter.ORMAuthenticationFilter;
import africa.semicolon.remApp.security.filter.ORMAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new ORMAuthenticationFilter(objectMapper,authenticationManager,jwtUtil);
        ORMAuthorizationFilter authorizationFilter = new ORMAuthorizationFilter(jwtUtil);
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authorizationFilter, ORMAuthenticationFilter.class)
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(endpoint -> endpoint.requestMatchers("/api/v1/employee/register", "/api/v1/company/register").permitAll())
                .authorizeHttpRequests(endpoint -> endpoint.requestMatchers("/api/v1/employee/complete-registration").hasAuthority("FRESH_USER"))
                .authorizeHttpRequests(endpoint -> endpoint.requestMatchers("/api/v1/generate-link").hasAuthority("ADMIN"))
                .build();
    }

}
