package africa.semicolon.remApp.services.notification;

import africa.semicolon.remApp.dtos.requests.EmailNotificationRequest;
import africa.semicolon.remApp.dtos.responses.SendMailResponse;

public interface MailService {

    SendMailResponse sendMail(EmailNotificationRequest emailNotificationRequest);
}
