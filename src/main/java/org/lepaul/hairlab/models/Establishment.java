package org.lepaul.hairlab.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "establishment")
public class Establishment{


    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private String address;
    private String ville;
    private String phone;
    private String email;
    private String urlImage;
    private String codeEstablishment;

    // Relation Establishment - User
    // @OneToMany(mappedBy = "establishment", cascade = CascadeType.ALL)
    // private List<Collaborator> collaborators = new ArrayList<>();

    public Establishment(){}

    public Establishment(String name, String address, String phone, String email, String urlImage, String codeEstablishment) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.urlImage = urlImage;
        this.codeEstablishment = codeEstablishment;
    }

    @Override
    public String toString() {
        return "Establishment={" + "id=" + id + ", name=" + name + ", address=" + address + ", ville=" + ville + ", phone=" + phone + ", email=" + email + ", code=" + codeEstablishment + '}';
    }

}
