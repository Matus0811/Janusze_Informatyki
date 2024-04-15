package org.project.projectmanagementsystem.api.dto;

import java.time.OffsetDateTime;

public record TaskDTO(
        String taskCode,
        String name,
        String description,
        String status,
        String priority,
        OffsetDateTime startDate,
        OffsetDateTime finishDate
) {
}
