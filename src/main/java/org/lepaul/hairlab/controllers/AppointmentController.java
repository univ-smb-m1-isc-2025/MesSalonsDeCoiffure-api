package org.lepaul.hairlab.controllers;

import org.lepaul.hairlab.DTOs.AppointmentDTO;
import org.lepaul.hairlab.models.Appointment;
import org.lepaul.hairlab.models.Collaborator;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.AppointmentRepository;
import org.lepaul.hairlab.repo.CollaboratorRepository;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.lepaul.hairlab.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    public Appointment addAppointment(@RequestBody AppointmentDTO request) {
        logger.info("POST /appointments/add called");

        User client = userRepo.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Collaborator collaborator = collaboratorRepo.findById(request.getCollaboratorId())
                .orElseThrow(() -> new RuntimeException("Collaborator not found"));

        Establishment establishment = estabRepo.findById(request.getEstablishmentId())
                .orElseThrow(() -> new RuntimeException("Establishment not found"));

        List<Appointment> conflicts = appointmentRepo.findConflictingAppointments(
                collaborator.getId(), request.getDateDebut(), request.getDateFin());

        // vérifie les conflis avec les heures
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Collaborator already has an appointment during that time");
        }

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

    @GetMapping("/byUserCollab")
    public List<Appointment> getAppointmentsByUserCollaborator(@RequestParam Long userId) {
        logger.info("GET /appointmentsHL/byUserCollab?userId={}", userId);

        Collaborator collaborator = collaboratorRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Collaborator not found for userId: " + userId));

        return appointmentRepo.findByCollaboratorId(collaborator.getId());
    }
}
