package africa.semicolon.remApp.dtos.responses;


import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class InvitationLinkResponse<T> {

    private String message;
    private String companyName;
    private String memberCount;
    private List<T> data;

}
