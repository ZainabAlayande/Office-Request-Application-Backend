package africa.semicolon.remApp.config;

import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import africa.semicolon.remApp.security.JwtUtil;
import africa.semicolon.remApp.services.employee.REMAEmployeeUtils;
import africa.semicolon.remApp.services.superAdmin.SuperAdminUtils;
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
    public SuperAdminUtils superAdminUtils(PasswordEncoder passwordEncoder) {
        return new SuperAdminUtils(passwordEncoder);
    }

    @Bean
    public REMAEmployeeUtils employeeUtils(EmployeeRepository employeeRepository,
                                           BioDataRepository bioDataRepository,
                                           JwtUtil jwtUtil) {
        return new REMAEmployeeUtils(employeeRepository, bioDataRepository, jwtUtil);
    }

}
