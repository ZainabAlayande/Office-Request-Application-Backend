package africa.semicolon.remApp.dtos.responses;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
public class ApiResponse<T> {

    private String message;
    private String theEnum;
    private boolean status;
    private T data;

}
