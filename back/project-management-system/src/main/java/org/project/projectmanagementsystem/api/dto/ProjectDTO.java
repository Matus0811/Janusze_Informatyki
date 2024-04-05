package org.project.projectmanagementsystem.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectDTO(
        UUID projectId,
        String name,
        String description,
        OffsetDateTime startDate,
        OffsetDateTime finishDate
) {

}
