package africa.semicolon.remApp.services.notification;

import africa.semicolon.remApp.config.MailConfig;
import africa.semicolon.remApp.dtos.requests.EmailNotificationRequest;
import africa.semicolon.remApp.dtos.responses.SendMailResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

//import static africa.semicolon.remApp.utils.EmailUtils.API_KEY_VALUE;
import static africa.semicolon.remApp.utils.EmailUtils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@AllArgsConstructor
@Slf4j
public class SendinBlueMailService implements MailService {

    private final MailConfig mailConfig;

    @Override
    public SendMailResponse sendMail(EmailNotificationRequest emailNotificationRequest) {
        System.out.println("send mail method");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(API_KEY_VALUE, mailConfig.getMailApiKey());
        httpHeaders.set("accept", APPLICATION_JSON_VALUE);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<EmailNotificationRequest> emailNotificationRequestEntity
                = new RequestEntity<>(emailNotificationRequest, httpHeaders, HttpMethod.POST, URI.create(EMAIL_URL));
        System.out.println("here 1");
        ResponseEntity<SendMailResponse> sendMailResponse =
                restTemplate.postForEntity(EMAIL_URL, emailNotificationRequestEntity, SendMailResponse.class);
        System.out.println("here 2");
        return sendMailResponse.getBody();
    }
}
