package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.Bug;

import java.util.UUID;

public record BugFormDTO(
        String title,
        String description,
        UUID projectId,
        UUID taskCode,
        String username,
        Bug.BugType bugType){
}
