package africa.semicolon.remApp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakeRequestForm {
    private String officeEmailAddress;
    private String requesterName;
    private String who;
    private String title;
    private String description;

}
