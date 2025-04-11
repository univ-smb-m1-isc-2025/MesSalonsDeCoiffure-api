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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddUser() throws Exception {
        // Création d'un utilisateur pour le test
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole("client");

        // Simulation du comportement de UserRepository
        when(userRepo.save(user)).thenReturn(user);

        // Simulation d'une requête POST et validation des résultats
        /*
        mockMvc.perform(post("/usersHL/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()));

         */
    }


}
