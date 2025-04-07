package org.lepaul.hairlab.controllers;

import lombok.Getter;
import org.lepaul.hairlab.models.Collaborator;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.models.User;
import org.lepaul.hairlab.repo.CollaboratorRepository;
import org.lepaul.hairlab.repo.EstablishmentRepository;
import org.lepaul.hairlab.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    public CollaboratorResponseDTO addCollaborator(@RequestBody CollaboratorDTO dto) {
        logger.info("POST /addCollaborator called with userId: {}, establishmentId: {}", dto.getUserId(), dto.getEstablishmentId());

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Establishment establishment = estabRepo.findById(dto.getEstablishmentId())
                .orElseThrow(() -> new RuntimeException("Establishment not found"));

        // Vérifier si déjà lié
        Optional<Collaborator> existing = collaboratorRepo.findByUserAndEstablishment(user, establishment);
        if (existing.isPresent()) {
            throw new RuntimeException("User already collaborator of this establishment");
        }

        Collaborator collaborator = new Collaborator(user, establishment);
        Collaborator savedCollaborator =  collaboratorRepo.save(collaborator);
        return new CollaboratorResponseDTO(savedCollaborator);
    }

    @GetMapping("/byEstab")
    public Iterable<Collaborator> getCollaboratorsByEstablishment(@RequestParam Long estabId) {
        return collaboratorRepo.findByEstablishmentId(estabId);
    }

    @Getter
    public static class CollaboratorDTO {
        private Long id;
        private Long userId;
        private Long establishmentId;
    }

    @Getter
    public static class CollaboratorResponseDTO {
        private Long id;
        private Long userId;
        private Long establishmentId;

        public CollaboratorResponseDTO(Collaborator collab) {
            this.id = collab.getId();
            this.userId = collab.getUser().getId();
            this.establishmentId = collab.getEstablishment().getId();
        }
    }
}
