package africa.semicolon.remApp.dtos.requests;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class EmployeeRegistrationRequest {

    private String firstName;
    private String lastName;
//    private String email;
    private String password;
}
