package africa.semicolon.remApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Requests {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String companyId;
    private String name;
    private String userId;
    private String emailAddress;
    private String title;
    private String description;
    private String body;
    private LocalDateTime timeRequested;
    private RequestStatus status;
    private String requestCount;


}
