package africa.semicolon.remApp.dtos.responses;

import africa.semicolon.remApp.models.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ViewRequestResponse {

    private String name;
    private String userId;
    private String emailAddress;
    private String title;
    private String description;
    private String body;
    private String timeRequested;
    private String status;
}
