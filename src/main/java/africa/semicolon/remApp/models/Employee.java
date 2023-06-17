package africa.semicolon.remApp.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.*;

import javax.annotation.processing.Generated;


@Setter
@Getter
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String officeLocation;
    private String offlineLine;
    private String position;
    private String profilePicture;
    private LocalDateTime timeCreated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BioData bioData;
}
