package africa.semicolon.remApp.dtos.responses;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompleteRegistrationResponse {

    private String firstName;
    private String lastName;
    private String password;
    private String officeLocation;
    private String officeLine;
    private String position;
    private String profilePicture;
    private String token;

}
