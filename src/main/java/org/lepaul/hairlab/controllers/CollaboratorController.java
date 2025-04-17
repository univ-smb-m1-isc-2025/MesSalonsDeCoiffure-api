package org.lepaul.hairlab.controllers;

import org.lepaul.hairlab.DTOs.CollaboratorDTO;
import org.lepaul.hairlab.models.Collaborator;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.CollaboratorRepository;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.lepaul.hairlab.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@RestController
@RequestMapping("/collaboratorsHL")
public class CollaboratorController {

    private final CollaboratorRepository collaboratorRepo;
    private final UserRepository userRepo;
    private final EstablishmentRepository estabRepo;
    private static final Logger logger = LoggerFactory.getLogger(CollaboratorController.class);

    public CollaboratorController(CollaboratorRepository collaboratorRepo, UserRepository userRepo, EstablishmentRepository estabRepo) {
        this.collaboratorRepo = collaboratorRepo;
        this.userRepo = userRepo;
        this.estabRepo = estabRepo;
    }

    @PostMapping("/addCollaborator")
    public org.lepaul.hairlab.DTOs.CollaboratorDTO addCollaborator(@RequestBody CollaboratorDTO dto) {
        logger.info("POST /addCollaborator called with userId: {}, establishmentId: {}", dto.getUserId(), dto.getEstablishmentId());

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Establishment establishment = estabRepo.findById(dto.getEstablishmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));

        // Vérifier si déjà lié
        Optional<Collaborator> existing = collaboratorRepo.findByUserAndEstablishment(user, establishment);
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already collaborator of this establishment");
        }

        Collaborator collaborator = new Collaborator(user, establishment);
        Collaborator savedCollaborator =  collaboratorRepo.save(collaborator);
        return new CollaboratorDTO(savedCollaborator);
    }

    @GetMapping("/byEstab")
    public Iterable<Collaborator> getCollaboratorsByEstablishment(@RequestParam Long estabId) {
        return collaboratorRepo.findByEstablishmentId(estabId);
    }

}
