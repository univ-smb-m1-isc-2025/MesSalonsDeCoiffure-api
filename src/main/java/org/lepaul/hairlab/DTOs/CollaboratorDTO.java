package org.lepaul.hairlab.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.lepaul.hairlab.models.Collaborator;

@Getter
@Setter
public class CollaboratorDTO {

    private Long id;
    private Long userId;
    private Long establishmentId;

    public CollaboratorDTO() {
    }

    public CollaboratorDTO(Collaborator collab) {
        this.id = collab.getId();
        this.userId = collab.getUser().getId();
        this.establishmentId = collab.getEstablishment().getId();
    }
}
