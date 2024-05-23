package org.project.projectmanagementsystem.api.dto;

import lombok.Builder;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record BugDTO(
        String serialNumber,
        String title,
        String description,
        Task task,
        String username,
        Bug.BugType bugType,
        OffsetDateTime reportDate,
        OffsetDateTime fixedDate
) {
}
