package org.project.projectmanagementsystem.api.dto;

public record TaskFormDTO(String name,
                          String description,
                          String status,
                          String priority,
                          String startDate,
                          String finishDate,
                          String projectId) {
}
