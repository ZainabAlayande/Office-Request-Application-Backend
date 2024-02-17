package africa.semicolon.remApp.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakeRequestForm {

    private String emailAddress;
    private String name;
    private String body;
    private String title;
    private String description;

}

/*
 * item
 * quantity
 * purpose
 * due date
 * additional information
 * */
