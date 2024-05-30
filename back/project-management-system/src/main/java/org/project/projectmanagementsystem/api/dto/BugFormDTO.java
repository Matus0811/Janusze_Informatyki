package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.Bug;

import java.time.OffsetDateTime;
import java.util.UUID;

public record BugFormDTO(
        String title,
        String description,
        UUID projectId,
        UUID taskCode,
        String username,
        OffsetDateTime reportDate,
        Bug.BugType bugType){
}
