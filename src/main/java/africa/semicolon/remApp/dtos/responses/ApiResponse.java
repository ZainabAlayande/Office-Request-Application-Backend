package africa.semicolon.remApp.dtos.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@Builder
@Data
@RestController
public class ApiResponse<T> {

    private String message;
    private boolean status;
    private T data;
}
