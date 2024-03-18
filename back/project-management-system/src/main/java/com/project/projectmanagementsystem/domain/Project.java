package com.project.projectmanagementsystem.domain;

import com.project.projectmanagementsystem.database.entities.BugEntity;
import com.project.projectmanagementsystem.database.entities.TaskEntity;
import com.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@With
@Data
@Value
@Builder
@EqualsAndHashCode
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

    public enum ProjectStatus {
        INITIAL, IN_PROGRESS, FINISHED
    }
}
