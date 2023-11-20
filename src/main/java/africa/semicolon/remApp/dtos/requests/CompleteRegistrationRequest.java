package africa.semicolon.remApp.dtos.requests;

import lombok.*;

@Setter
@Getter
public class CompleteRegistrationRequest {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String password;

    private String officeLocation;
    private String offlineLine;
    private String position;
    private String profilePicture;


}
