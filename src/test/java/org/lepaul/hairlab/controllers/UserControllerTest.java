package org.lepaul.hairlab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUsers() throws Exception {
        // Mock du comportement du repository pour retourner une liste vide
        when(userRepository.findAll()).thenReturn(List.of());

        // Test du endpoint GET /usersHL/users
        mockMvc.perform(get("/usersHL/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]")); // Récupère une liste vide en JSON
    }

    @Test
    public void testAddUser() throws Exception {
        // Création d'un utilisateur simulé
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole("client");

        // Mock du comportement de save pour retourner l'utilisateur sauvegardé
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Test du endpoint POST /usersHL/addUser
        mockMvc.perform(post("/usersHL/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))) // Convertit l'utilisateur en JSON
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testCheckUserExists_Found() throws Exception {
        // Création d'un utilisateur simulé
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123456");

        // Mock du comportement du repository pour simuler qu'un utilisateur a été trouvé
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));

        // Test du endpoint POST /usersHL/checkUser
        mockMvc.perform(post("/usersHL/checkUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trouve").value(true))
                .andExpect(jsonPath("$.user.email").value("test@example.com"));
    }

    @Test
    public void testCheckUserExists_NotFound() throws Exception {
        // Création d'un utilisateur simulé
        User user = new User();
        user.setEmail("notfound@example.com");
        user.setPassword("wrongpassword");

        // Mock du comportement du repository pour simuler qu'aucun utilisateur n'a été trouvé
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.empty());

        // Test du endpoint POST /usersHL/checkUser
        mockMvc.perform(post("/usersHL/checkUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trouve").value(false))
                .andExpect(jsonPath("$.user").isEmpty());
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("AncienNom");
        existingUser.setEmail("old@mail.com");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("NouveauNom");
        updatedUser.setEmail("new@mail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(post("/usersHL/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("NouveauNom"))
                .andExpect(jsonPath("$.email").value("new@mail.com"));
    }

    @Test
    public void testUpdateUser_UserNotFound() throws Exception {
        User user = new User();
        user.setId(999L);
        user.setFirstName("NouveauNom");

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/usersHL/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id 999"));
    }


}