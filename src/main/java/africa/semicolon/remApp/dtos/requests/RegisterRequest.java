package africa.semicolon.remApp.dtos.requests;

import lombok.*;

@Setter
@Getter
public class RegisterRequest {

    private String officeEmailAddress;
    private String password;

}
