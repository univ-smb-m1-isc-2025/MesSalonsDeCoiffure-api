package org.lepaul.hairlab.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lepaul.hairlab.DTOs.CollaboratorDTO;
import org.lepaul.hairlab.models.Collaborator;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.CollaboratorRepository;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.lepaul.hairlab.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@WebMvcTest(CollaboratorController.class)
public class CollaboratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollaboratorRepository collaboratorRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private EstablishmentRepository estabRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private User mockUser;
    private Establishment mockEstab;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);

        mockEstab = new Establishment();
        mockEstab.setId(2L);
    }

    @Test
    public void testAddCollaborator_Success() throws Exception {
        Collaborator collab = new Collaborator(mockUser, mockEstab);
        CollaboratorDTO dto = new CollaboratorDTO(collab);  // Utilisation du constructeur du DTO

        Collaborator mockCollab = new Collaborator(mockUser, mockEstab);
        mockCollab.setId(3L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(estabRepo.findById(2L)).thenReturn(Optional.of(mockEstab));
        when(collaboratorRepo.findByUserAndEstablishment(mockUser, mockEstab)).thenReturn(Optional.empty());
        when(collaboratorRepo.save(any(Collaborator.class))).thenReturn(mockCollab);

        mockMvc.perform(post("/collaboratorsHL/addCollaborator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.establishmentId").value(2L));
    }

    @Test
    public void testAddCollaborator_UserNotFound() throws Exception {
        CollaboratorDTO dto = new CollaboratorDTO(new Collaborator(new User(), mockEstab));  // Utilisation d'un constructeur avec un collaborateur "vide"
        dto.setUserId(99L);  // L'ID qui ne correspond à aucun utilisateur

        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/collaboratorsHL/addCollaborator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void testAddCollaborator_EstabNotFound() throws Exception {
        CollaboratorDTO dto = new CollaboratorDTO(new Collaborator(mockUser, new Establishment()));  // Utilisation d'un collaborateur "vide"
        dto.setUserId(1L);  // Utilisateur valide
        dto.setEstablishmentId(99L);  // ID de l'établissement qui ne correspond à aucun

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(estabRepo.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/collaboratorsHL/addCollaborator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Establishment not found"));
    }

    @Test
    public void testAddCollaborator_AlreadyLinked() throws Exception {
        Collaborator collab = new Collaborator(mockUser, mockEstab);
        CollaboratorDTO dto = new CollaboratorDTO(collab);  // Utilisation du constructeur du DTO

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(estabRepo.findById(2L)).thenReturn(Optional.of(mockEstab));
        when(collaboratorRepo.findByUserAndEstablishment(mockUser, mockEstab)).thenReturn(Optional.of(collab));

        mockMvc.perform(post("/collaboratorsHL/addCollaborator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())  // Attente de BadRequest pour ce cas
                .andExpect(content().string("User already collaborator of this establishment"));
    }
}
