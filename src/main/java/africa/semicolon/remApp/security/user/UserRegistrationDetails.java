package africa.semicolon.remApp.security.user;

import africa.semicolon.remApp.models.BioData;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.repositories.BioDataRepository;
import africa.semicolon.remApp.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDetails implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final BioDataRepository bioDataRepository;

    public UserRegistrationDetails (EmployeeRepository employeeRepository, BioDataRepository bioDataRepository) {
        this.employeeRepository = employeeRepository;
        this.bioDataRepository = bioDataRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var bioData = bioDataRepository.findByOfficeEmailAddress(username);
        BioData foundBioData = bioData.get();
        var employee = employeeRepository.findByBioData(foundBioData);
        Employee foundEmployee = employee.get();
        return (UserDetails) foundEmployee;
    }


}
