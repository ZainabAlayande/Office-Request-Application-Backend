package africa.semicolon.remApp.dtos.responses;

import africa.semicolon.remApp.enums.MemberInviteStatus;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeListResponse {

    private String name;
    private String email;
    private String profilePicture;
    private MemberInviteStatus inviteStatus;
}
