package africa.semicolon.remApp.models;

import africa.semicolon.remApp.enums.MemberInviteStatus;
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
    private String uniqueId;
    private String lastName;
    private String password;
    private String email;
    private String officeLocation;
    private String officeLine;
    private String position;
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    @Column(name = "invite_status", columnDefinition = "ENUM('PENDING', 'JOINED')")
    private MemberInviteStatus inviteStatus;

    private LocalDateTime timeCreated;

    @ManyToOne
    private Company company;

    private List<Role> roles = new ArrayList<>();


}
