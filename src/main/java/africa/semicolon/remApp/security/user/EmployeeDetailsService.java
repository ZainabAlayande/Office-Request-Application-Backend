package africa.semicolon.remApp.security.user;

import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.services.employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Primary
@Component
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee foundEmployee = employeeService.findEmployeeByEmail(username);
        if (foundEmployee.getId() == null ) throw new REMAException("Employee not found");
        UserDetails userDetails = new EmployeeDetails(foundEmployee);
        return userDetails;
    }
}
