package org.project.projectmanagementsystem.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@With
@Value
@Builder
public class Project {

    UUID projectId;
    String name;
    String description;
    Project.ProjectStatus projectStatus;
    OffsetDateTime startDate;
    OffsetDateTime finishDate;
    Set<UserProjectRole> userProjectRoles;
    Set<Task> tasks;
    Set<Bug> bugs;

    public static Project buildProjectFromForm(ProjectForm projectForm) {
        Project project = Project.builder()
                .name(projectForm.getName())
                .projectStatus(Project.ProjectStatus.INITIAL)
                .description(projectForm.getDescription())
                .startDate(OffsetDateTime.now())
                .build();

        if (!projectForm.getFinishDate().isBlank()) {
            project = project.withFinishDate(OffsetDateTime.parse(projectForm.getFinishDate()));
        }

        return project;
    }
    public enum ProjectStatus {
        INITIAL, IN_PROGRESS, FINISHED
    }
}
