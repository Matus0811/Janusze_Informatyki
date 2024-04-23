package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.Task;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TaskFormDTO(String name,
                          String description,
                          Task.TaskStatus status,
                          Task.Priority priority,
                          OffsetDateTime finishDate,
                          UUID projectId) {
}
