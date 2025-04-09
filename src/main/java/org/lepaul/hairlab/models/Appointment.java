package org.lepaul.hairlab.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp dateDebut;
    private Timestamp dateFin;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    private Collaborator collaborator;

    @ManyToOne
    @JoinColumn(name = "establishment_id", nullable = false)
    private Establishment establishment;

    public Appointment() {}

    public Appointment(Timestamp dateDebut, Timestamp dateFin, User client, Collaborator collaborator, Establishment establishment) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.client = client;
        this.collaborator = collaborator;
        this.establishment = establishment;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", client=" + client.getEmail() +
                ", collaborator=" + collaborator.getUser().getEmail() +
                ", establishment=" + establishment.getName() +
                '}';
    }
}
