package org.project.projectmanagementsystem.api.dto;

import java.util.List;

public record AssignFormDTO(String taskCode, List<String> userEmails, String projectId) {
}
