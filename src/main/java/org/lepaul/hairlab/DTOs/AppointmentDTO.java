package org.lepaul.hairlab.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AppointmentDTO {

    private Long clientId;
    private Long collaboratorId;
    private Long establishmentId;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private String description;
    private Long price;
}
