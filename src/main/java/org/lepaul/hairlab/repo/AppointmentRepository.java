package org.lepaul.hairlab.repo;

import org.lepaul.hairlab.models.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByEstablishmentId(Long establishmentId);
    List<Appointment> findByCollaboratorId(Long collaboratorId);
    List<Appointment> findByClientId(Long clientId);
    @Query(""" 
        SELECT a FROM Appointment a WHERE a.collaborator.id = :collaboratorId
        AND ( (:start < a.dateFin AND :end > a.dateDebut))
        """)
    List<Appointment> findConflictingAppointments(
            @Param("collaboratorId") Long collaboratorId,
            @Param("start") Timestamp start,
            @Param("end") Timestamp end
    );
}
