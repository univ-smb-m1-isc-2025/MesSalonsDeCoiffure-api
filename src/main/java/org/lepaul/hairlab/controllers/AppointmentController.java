package org.lepaul.hairlab.controllers;

import lombok.Getter;
import org.lepaul.hairlab.models.Appointment;
import org.lepaul.hairlab.models.Collaborator;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.AppointmentRepository;
import org.lepaul.hairlab.repo.CollaboratorRepository;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.lepaul.hairlab.repo.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;

@RestController
@RequestMapping("/appointmentsHL")
public class AppointmentController {

    private final AppointmentRepository appointmentRepo;
    private final UserRepository userRepo;
    private final CollaboratorRepository collaboratorRepo;
    private final EstablishmentRepository estabRepo;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    // constructor
    public AppointmentController(AppointmentRepository appointmentRepo, UserRepository userRepo, CollaboratorRepository collaboratorRepo, EstablishmentRepository estabRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
        this.collaboratorRepo = collaboratorRepo;
        this.estabRepo = estabRepo;
    }

    @PostMapping("/addAppointment")
    public Appointment addAppointment(@RequestBody AppointmentRequest request) {
        logger.info("POST /appointments/add called");

        User client = userRepo.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Collaborator collaborator = collaboratorRepo.findById(request.getCollaboratorId())
                .orElseThrow(() -> new RuntimeException("Collaborator not found"));

        Establishment establishment = estabRepo.findById(request.getEstablishmentId())
                .orElseThrow(() -> new RuntimeException("Establishment not found"));

        Appointment appointment = new Appointment(request.getDateDebut(), request.getDateFin(), request.getDescription(), request.getPrice(), client, collaborator, establishment);
        return appointmentRepo.save(appointment);
    }

    @GetMapping("/byEstab")
    public Iterable<Appointment> getAppointmentsByEstablishment(@RequestParam Long establishmentId) {
        return appointmentRepo.findByEstablishmentId(establishmentId);
    }

    @GetMapping("/byCollab")
    public Iterable<Appointment> getAppointmentsByCollaborator(@RequestParam Long collaboratorId) {
        return appointmentRepo.findByCollaboratorId(collaboratorId);
    }

    @GetMapping("/byClient")
    public Iterable<Appointment> getAppointmentsByClient(@RequestParam Long clientId) {
        return appointmentRepo.findByClientId(clientId);
    }

    // Pour gérer la forme des requêtes
    @Getter
    public static class AppointmentRequest {
        private Long clientId;
        private Long collaboratorId;
        private Long establishmentId;
        private Timestamp dateDebut;
        private Timestamp dateFin;
        private String description;
        private Long price;
    }
}
