package org.lepaul.hairlab.controllers;

import lombok.Getter;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import java.util.Optional;

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

    @PostMapping(value = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User usert) {
        logger.info("POST /addUser called : ADD USER {}", usert);
        return this.userRepository.save(usert);
    }

    @PostMapping("/checkUser")
    public returnRequestCheckUser checkUserExists(@RequestBody User user) {
        logger.info("POST /checkUser called with email: {}", user.getEmail());

        returnRequestCheckUser userCheck = new returnRequestCheckUser();

        if (user.getEmail() == null || user.getPassword() == null) {
            logger.error("Missing email or password");
            userCheck.user = Optional.of(user);
            userCheck.trouve = false;
        }
        if(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent()){
            userCheck.trouve = true;
            userCheck.user = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        }

        return userCheck;
    }

    @Getter
    public static class returnRequestCheckUser{
        private Optional<User> user;
        private boolean trouve;
    }

}
