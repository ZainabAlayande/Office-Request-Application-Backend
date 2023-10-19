package africa.semicolon.remApp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static africa.semicolon.remApp.utils.EmailUtils.MAIL_API_KEY;

@Getter
@Configuration
public class MailConfig {

    @Value(MAIL_API_KEY)
    private String mailApiKey;



}
