package org.lepaul.hairlab.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(){}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    @Override
    public String toString() {
        return "User{firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "}";
    }
}
