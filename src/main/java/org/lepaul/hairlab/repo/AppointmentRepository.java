package org.lepaul.hairlab.repo;

import org.lepaul.hairlab.models.Appointment;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByEstablishmentId(Long establishmentId);
    List<Appointment> findByCollaboratorId(Long collaboratorId);
    List<Appointment> findByClientId(Long clientId);
}
