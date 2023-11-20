package africa.semicolon.remApp.config;

import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import africa.semicolon.remApp.services.employee.REMAEmployeeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BioDataRepository bioDataRepository;

    private JwtUtil jwtUtil;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public REMAEmployeeUtils employeeUtils() {
      return new REMAEmployeeUtils(employeeRepository,bioDataRepository, jwtUtil());
    }

}
