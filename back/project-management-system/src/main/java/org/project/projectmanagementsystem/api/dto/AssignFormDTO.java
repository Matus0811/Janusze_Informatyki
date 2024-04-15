package org.project.projectmanagementsystem.api.dto;

import java.util.List;
import java.util.UUID;

public record AssignFormDTO(String taskCode, List<String> userEmails, UUID projectId) {
}
