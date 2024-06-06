package org.project.projectmanagementsystem.api.dto;

public record UserTaskDTO(
        TaskDTO task,
        Boolean finished
) {
}
