package africa.semicolon.remApp.dtos.requests;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyRegistrationRequest {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;


}
