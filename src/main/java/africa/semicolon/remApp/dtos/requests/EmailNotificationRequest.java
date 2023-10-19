package africa.semicolon.remApp.dtos.requests;

import africa.semicolon.remApp.utils.EmailUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

import static africa.semicolon.remApp.utils.EmailUtils.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailNotificationRequest {

    @JsonProperty(EmailUtils.SENDER)
    private Sender emailSender;

    @JsonProperty(TO)
    private Set<Recipient> recipient;

    @JsonProperty(SUBJECT)
    private String subject;

    @JsonProperty(HTML_CONTENT_VALUE)
    private String content;
}
