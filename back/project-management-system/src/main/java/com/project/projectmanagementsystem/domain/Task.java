package com.project.projectmanagementsystem.domain;

import com.project.projectmanagementsystem.database.entities.CommentEntity;
import com.project.projectmanagementsystem.database.entities.ProjectEntity;
import com.project.projectmanagementsystem.database.entities.UserTaskEntity;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Data
@Value
@Builder
@EqualsAndHashCode
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
