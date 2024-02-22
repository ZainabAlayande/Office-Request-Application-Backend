package africa.semicolon.remApp.dtos.responses;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Response {

    private String status;
    private String email;
    private String profilePicture;
//    private String role;
}
