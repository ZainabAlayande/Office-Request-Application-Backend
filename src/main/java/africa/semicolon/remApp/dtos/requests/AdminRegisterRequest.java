package africa.semicolon.remApp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterRequest {

    private String officeEmailAddress;
    private String password;
    private String position;

}
