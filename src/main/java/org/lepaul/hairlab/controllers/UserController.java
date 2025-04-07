package org.lepaul.hairlab.controllers;

import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/usersHL")
public class UserController {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // simple handlers for the API
    @GetMapping("/users")
    public Iterable<User> getUsers() {
        logger.info("GET /users called");
        return this.userRepository.findAll();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User usert) {
        logger.info("POST /addUser called : ADD USER {}", usert);
        return this.userRepository.save(usert);
    }

    @PostMapping("/checkUser")
    public boolean checkUserExists(@RequestBody User user) {
        logger.info("POST /checkUser called with email: {}", user.getEmail());

        if (user.getEmail() == null || user.getPassword() == null) {
            logger.error("Missing email or password");
            return false;
        }

        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent();
    }

}
