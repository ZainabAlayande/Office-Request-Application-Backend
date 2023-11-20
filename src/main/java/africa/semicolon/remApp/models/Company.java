package africa.semicolon.remApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    private Long id;
    private String companyName;
    private String companyUniqueID;
    private String companyEmail;
    private String companyPassword;
    private String companySize;
    private LocalDateTime timeCreated;

}
