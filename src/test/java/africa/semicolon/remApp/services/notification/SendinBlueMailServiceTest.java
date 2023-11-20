package africa.semicolon.remApp.services.notification;

import africa.semicolon.remApp.dtos.requests.EmailNotificationRequest;
import africa.semicolon.remApp.dtos.requests.Recipient;
import africa.semicolon.remApp.dtos.requests.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SendinBlueMailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail() {
        Sender sender = new Sender("orm app", "noreply@orma.com");
        Recipient recipient = new Recipient("alayandezainab64@gmail.com", "Zainab");
        EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest();
        emailNotificationRequest.setEmailSender(sender);
        emailNotificationRequest.setRecipient(Set.of(recipient));
        emailNotificationRequest.setContent("<p>My First Email</p>");
        emailNotificationRequest.setSubject("Welcome Zen of Python");
        var response = mailService.sendMail(emailNotificationRequest);
        assertThat(response).isNotNull();
    }

}
