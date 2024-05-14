package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.Project;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectDTO(
        UUID projectId,
        String name,
        String description,
        Project.ProjectStatus projectStatus,
        OffsetDateTime startDate,
        OffsetDateTime finishDate
) {

}
