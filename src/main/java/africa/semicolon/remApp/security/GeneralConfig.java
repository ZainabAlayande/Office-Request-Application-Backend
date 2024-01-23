package africa.semicolon.remApp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static africa.semicolon.remApp.utils.AppUtils.JWT_SECRET;


@Configuration
public class GeneralConfig {

    @Value(JWT_SECRET)
    private String secret;

    @Bean
    public JwtUtil jwtUtils() {
        return new JwtUtil();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }


}
