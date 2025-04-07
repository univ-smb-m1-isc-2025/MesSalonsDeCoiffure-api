package org.lepaul.hairlab.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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
    private String role;

    // Relation User - Establishment
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // private List<Collaborator> establishments = new ArrayList<>();

    public User(){}

    public User(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    @Override
    public String toString() {
        return "User{firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", role=" + role + "}";
    }
}
