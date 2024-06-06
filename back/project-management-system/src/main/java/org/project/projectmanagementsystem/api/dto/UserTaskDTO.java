package org.project.projectmanagementsystem.api.dto;

public record UserTaskDTO(
        UserDTO user,
        TaskDTO task,
        Boolean finished
) {
}
