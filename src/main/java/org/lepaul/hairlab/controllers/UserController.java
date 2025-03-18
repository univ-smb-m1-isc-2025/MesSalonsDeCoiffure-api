package org.lepaul.hairlab.controllers;

import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/api")
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
    public User addUser(@RequestBody User user) {
        logger.info("POST /addUser called");
        return this.userRepository.save(user);
    }
}
