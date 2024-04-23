package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.Bug;

import java.util.UUID;

public record BugFormDTO(
        String title,
        String description,
        UUID projectId,
        String userEmail,
        Bug.BugType bugType){
}
