package africa.semicolon.remApp.models;

import africa.semicolon.remApp.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<Role> roles = new ArrayList<>();


}
