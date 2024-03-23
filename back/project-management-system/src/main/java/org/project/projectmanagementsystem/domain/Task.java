package org.project.projectmanagementsystem.domain;

import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
public class Task {

    Long taskId;
    String taskCode;
    String name;
    String description;
    Task.TaskStatus status;
    Task.Priority priority;
    OffsetDateTime startDate;
    OffsetDateTime finishDate;
    ProjectEntity project;
    Set<Comment> comments;
    Set<UserTask> userTasks;

    public enum TaskStatus{
        CONCEPT, IN_PROGRESS, FINISHED, REJECTED, BUG, TO_DO
    }

    public enum Priority{
        LOW,MEDIUM,HIGH
    }
}
