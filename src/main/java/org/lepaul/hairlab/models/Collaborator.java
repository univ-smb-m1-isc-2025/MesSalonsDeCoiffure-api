package org.lepaul.hairlab.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "collaborators")
@Getter
@Setter
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "establishment_id", nullable = false)
    private Establishment establishment;

    public Collaborator() {}

    public Collaborator(User user, Establishment establishment) {
        this.user = user;
        this.establishment = establishment;
    }

    @Override
    public String toString() {
        return "Collaborator{id=" + id + ", user=" + user.getEmail() + ", establishment=" + establishment.getName() + "}";
    }
}
