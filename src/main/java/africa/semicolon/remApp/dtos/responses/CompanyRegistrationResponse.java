package africa.semicolon.remApp.dtos.responses;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class CompanyRegistrationResponse {

    private String companyName;
    private String companyEmail;
//    private String companyPassword;
//    private String companySize;
    private String companyUniqueID;
//    private String token;
//
//    private String superAdminFirstName;
//    private String superAdminLastName;
//    private String superAdminEmail;

}
