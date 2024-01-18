package africa.semicolon.remApp.security.user;

import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.services.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@AllArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee foundEmployee = null;
        try {
            foundEmployee = employeeService.findEmployeeByEmail(username);
        } catch (REMAException e) {
            throw new RuntimeException(e);
        }
        UserDetails userDetails = new EmployeeDetails(foundEmployee);
        return userDetails;
    }
}
