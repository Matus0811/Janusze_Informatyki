package org.project.projectmanagementsystem.api.dto;

public record TaskDTO(
        String taskCode,
        String name,
        String description,
        String status,
        String priority,
        String startDate
) {
}
