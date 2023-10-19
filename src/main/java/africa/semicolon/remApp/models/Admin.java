package africa.semicolon.remApp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String officeLocation;
    private String officeEmailAddress;
    private String password;
    private String position;
    private String profilePicture;
    private String officeLine;
    private LocalDateTime timeCreated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BioData bioData;

}
