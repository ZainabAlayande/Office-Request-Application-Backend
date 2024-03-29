package africa.semicolon.remApp.models;

import africa.semicolon.remApp.enums.Role;
import jakarta.persistence.*;
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
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String uniqueID;
    private String email;
    private String password;
    private String confirmPassword;
    private int memberCount;
    private LocalDateTime timeCreated;
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employee> employee = new ArrayList<>();


}
