package org.lepaul.hairlab.repo;

import org.lepaul.hairlab.models.Establishment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstablishmentRepository extends CrudRepository<Establishment, Long> {
    @Query("SELECT e FROM Establishment e WHERE e.id = :id")
    Optional<Establishment> findById(@Param("id") Long id);
}
