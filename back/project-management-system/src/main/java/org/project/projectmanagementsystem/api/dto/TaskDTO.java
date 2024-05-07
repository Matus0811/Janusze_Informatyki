package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.Task;

import java.time.OffsetDateTime;

public record TaskDTO(
        String taskCode,
        String name,
        String description,
        Task.TaskStatus status,
        Task.Priority priority,
        OffsetDateTime startDate,
        OffsetDateTime finishDate
) {
}
