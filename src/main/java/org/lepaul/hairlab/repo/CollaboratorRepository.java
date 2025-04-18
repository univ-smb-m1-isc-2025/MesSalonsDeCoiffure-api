package org.lepaul.hairlab.repo;

import org.lepaul.hairlab.models.Collaborator;
import org.lepaul.hairlab.models.Establishment;
import org.lepaul.hairlab.models.User;

import java.util.List;
import java.util.Optional;

public interface CollaboratorRepository extends CrudRepository<Collaborator, Long> {

    List<Collaborator> findByEstablishmentId(Long estabId);
    Optional<Collaborator> findByUserAndEstablishment(User user, Establishment establishment);
    Optional<Collaborator> findByUserId(Long userId);

}
