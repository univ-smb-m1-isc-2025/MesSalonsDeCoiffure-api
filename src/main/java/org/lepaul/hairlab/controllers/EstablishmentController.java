package org.lepaul.hairlab.controllers;

import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/estabHL")
public class EstablishmentController {

    private final EstablishmentRepository estabRepo;
    private static final Logger logger = LoggerFactory.getLogger(EstablishmentController.class);

    public EstablishmentController(EstablishmentRepository establishmentRepository) {
        this.estabRepo = establishmentRepository;
    }

    @GetMapping("/estabs")
    public Iterable<Establishment> getEstablishments() {
        logger.info("GET /estabs called");
        return this.estabRepo.findAll();
    }

    @PostMapping("/addEstab")
    public Establishment addEstab(@RequestBody Establishment estabIn) {
        logger.info("POST /addEstab called : ADD ESTABLISHMENT {}", estabIn);
        return this.estabRepo.save(estabIn);
    }

    @PostMapping("/getById")
    public Establishment getEstablishmentById(@RequestParam Long id) {
        logger.info("POST /byId called with id: {}", id);
        return this.estabRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));

    }
}
