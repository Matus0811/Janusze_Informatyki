package org.project.projectmanagementsystem.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TaskFormDTO(String name,
                          String description,
                          String status,
                          String priority,
                          OffsetDateTime startDate,
                          OffsetDateTime finishDate,
                          UUID projectId) {
}
