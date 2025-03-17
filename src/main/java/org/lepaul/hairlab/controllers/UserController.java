package org.lepaul.hairlab.controllers;

import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // simple handlers for the API
    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }
}
