package ru.interview.models;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String password;
    private String firstName;
    private String secondName;
    private String eMail;


    public User(String name, String password, String firstName, String secondName, String eMail) {
        this.name = name;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.eMail = eMail;
    }
}
