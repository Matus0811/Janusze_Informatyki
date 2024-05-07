package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.Task;

public record ProjectTaskStatusCount(
        Task.TaskStatus status,
        Long count) {
}
