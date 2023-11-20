package africa.semicolon.remApp.models;

import africa.semicolon.remApp.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdmin {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String officeLocation;
    private String officeLine;
    private String position;
    private String profilePicture;
    private LocalDateTime timeCreated;
    private List<Role> roles = new ArrayList<>();
}
