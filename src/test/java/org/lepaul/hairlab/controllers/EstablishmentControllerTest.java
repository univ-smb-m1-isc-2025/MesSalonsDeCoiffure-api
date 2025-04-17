package org.lepaul.hairlab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstablishmentController.class)
public class EstablishmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EstablishmentRepository establishmentRepository;

    @Test
    public void testGetEstablishments() throws Exception {
        // Simule le retour d'une liste vide
        when(establishmentRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/estabHL/estabs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    public void testAddEstablishment() throws Exception {
        // Création d'un établissement simulé
        Establishment estab = new Establishment();
        estab.setName("Salon Paul");
        estab.setAddress("123 rue des Coiffeurs");
        estab.setVille("Annecy");
        estab.setPhone("0123456789");
        estab.setCodeEstablishment("5e&jn7dnh&e7");

        // Mock du comportement de save
        when(establishmentRepository.save(any(Establishment.class))).thenReturn(estab);

        mockMvc.perform(post("/estabHL/addEstab")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estab)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Salon Paul"))
                .andExpect(jsonPath("$.address").value("123 rue des Coiffeurs"));
    }

    @Test
    public void testGetEstablishmentById_Found() throws Exception {
        // Simule un établissement existant
        Establishment estab = new Establishment();
        estab.setId(1L);
        estab.setName("Salon Paul");
        estab.setAddress("123 rue des Coiffeurs");
        estab.setVille("Annecy");
        estab.setCodeEstablishment("5e&jn7dnh&e7");
        estab.setPhone("0123456789");

        // Mock du repository pour retourner l’établissement
        when(establishmentRepository.findById(1L)).thenReturn(java.util.Optional.of(estab));

        mockMvc.perform(post("/estabHL/getById")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Salon Paul"))
                .andExpect(jsonPath("$.address").value("123 rue des Coiffeurs"));
    }

    @Test
    public void testGetEstablishmentById_NotFound() throws Exception {
        // Mock : aucun établissement trouvé pour l'id 99
        when(establishmentRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(post("/estabHL/getById")
                        .param("id", "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
