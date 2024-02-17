package africa.semicolon.remApp.models;

import africa.semicolon.remApp.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;



@Setter
@Getter
@Entity
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String officeLocation;
    private String officeLine;
    private String position;
    private String profilePicture;
    private String inviteStatus;
    private LocalDateTime timeCreated;

    private List<Role> roles = new ArrayList<>();

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private BioData bioData;
}
