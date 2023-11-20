package africa.semicolon.remApp.dtos.requests;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyRegistrationRequest {

    private String companyName;
    private String companyEmail;
    private String companyPassword;
    private String companySize;

    private String superAdminFirstName;
    private String superAdminLastName;
    private String superAdminEmail;
    private String superAdminPassword;
}
