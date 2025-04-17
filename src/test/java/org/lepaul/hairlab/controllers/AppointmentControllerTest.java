package org.lepaul.hairlab.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.lepaul.hairlab.DTOs.AppointmentDTO;
import org.lepaul.hairlab.models.Appointment;
import org.lepaul.hairlab.models.Collaborator;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.AppointmentRepository;
import org.lepaul.hairlab.repo.CollaboratorRepository;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.lepaul.hairlab.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppointmentRepository appointmentRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private CollaboratorRepository collaboratorRepo;

    @MockBean
    private EstablishmentRepository estabRepo;

    @Test
    public void testAddAppointment_Success() throws Exception {
        // Création des entités simulées
        User client = new User();
        client.setId(1L);
        Collaborator collab = new Collaborator();
        collab.setId(2L);
        Establishment estab = new Establishment();
        estab.setId(3L);

        Appointment appointment = new Appointment(
                Timestamp.valueOf("2025-05-01 10:00:00"),
                Timestamp.valueOf("2025-05-01 11:00:00"),
                "Coupe",
                30L,
                client, collab, estab
        );

        // Mock des repos
        when(userRepo.findById(1L)).thenReturn(Optional.of(client));
        when(collaboratorRepo.findById(2L)).thenReturn(Optional.of(collab));
        when(estabRepo.findById(3L)).thenReturn(Optional.of(estab));
        when(appointmentRepo.save(any(Appointment.class))).thenReturn(appointment);

        // Création du DTO
        AppointmentDTO dto = new AppointmentDTO();
        dto.setClientId(1L);
        dto.setCollaboratorId(2L);
        dto.setEstablishmentId(3L);
        dto.setDateDebut(Timestamp.valueOf("2025-05-01 10:00:00"));
        dto.setDateFin(Timestamp.valueOf("2025-05-01 11:00:00"));
        dto.setDescription("Coupe");
        dto.setPrice(30L);

        mockMvc.perform(post("/appointmentsHL/addAppointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Coupe"))
                .andExpect(jsonPath("$.price").value(30));
    }

    @Test
    public void testAddAppointment_ClientNotFound() throws Exception {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setClientId(999L);
        dto.setCollaboratorId(2L);
        dto.setEstablishmentId(3L);
        dto.setDateDebut(Timestamp.valueOf("2025-05-01 10:00:00"));
        dto.setDateFin(Timestamp.valueOf("2025-05-01 11:00:00"));
        dto.setDescription("Coupe");
        dto.setPrice(30L);

        when(userRepo.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/appointmentsHL/addAppointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Client not found"));
    }

    @Test
    public void testGetAppointmentsByEstablishment() throws Exception {
        when(appointmentRepo.findByEstablishmentId(1L)).thenReturn(List.of());

        mockMvc.perform(get("/appointmentsHL/byEstab")
                        .param("establishmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetAppointmentsByCollaborator() throws Exception {
        when(appointmentRepo.findByCollaboratorId(2L)).thenReturn(List.of());

        mockMvc.perform(get("/appointmentsHL/byCollab")
                        .param("collaboratorId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetAppointmentsByClient() throws Exception {
        when(appointmentRepo.findByClientId(3L)).thenReturn(List.of());

        mockMvc.perform(get("/appointmentsHL/byClient")
                        .param("clientId", "3"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testAddAppointment_CollaboratorHasConflict() throws Exception {
        // Création des entités simulées
        User client = new User();
        client.setId(1L);
        Collaborator collab = new Collaborator();
        collab.setId(2L);
        Establishment estab = new Establishment();
        estab.setId(3L);

        // Plage horaire demandée
        Timestamp start = Timestamp.valueOf("2025-05-01 10:00:00");
        Timestamp end = Timestamp.valueOf("2025-05-01 11:00:00");

        // Rendez-vous déjà existant en conflit
        Appointment existing = new Appointment(
                Timestamp.valueOf("2025-05-01 10:30:00"),
                Timestamp.valueOf("2025-05-01 11:30:00"),
                "Déjà pris",
                40L,
                client, collab, estab
        );

        AppointmentDTO dto = new AppointmentDTO();
        dto.setClientId(1L);
        dto.setCollaboratorId(2L);
        dto.setEstablishmentId(3L);
        dto.setDateDebut(start);
        dto.setDateFin(end);
        dto.setDescription("Nouveau rdv");
        dto.setPrice(30L);

        // Mock des repos
        when(userRepo.findById(1L)).thenReturn(Optional.of(client));
        when(collaboratorRepo.findById(2L)).thenReturn(Optional.of(collab));
        when(estabRepo.findById(3L)).thenReturn(Optional.of(estab));
        when(appointmentRepo.findConflictingAppointments(2L, start, end))
                .thenReturn(List.of(existing));

        mockMvc.perform(post("/appointmentsHL/addAppointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound()) // au lieu de isInternalServerError()
                .andExpect(content().string("Collaborator already has an appointment during that time"));
    }

}