package africa.semicolon.remApp.dtos.requests;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCount {

    private String countOfAssignedRequest;
    private String countOfPendingRequest;
    private String countOfApprovedRequest;
    private String countOfDeclinedRequest;
}
