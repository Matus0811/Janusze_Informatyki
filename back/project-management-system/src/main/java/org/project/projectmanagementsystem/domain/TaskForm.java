package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.UUID;

@With
@Value
@Builder
public class TaskForm {
    String name;
    String description;
    Task.TaskStatus status;
    Task.Priority priority;
    OffsetDateTime finishDate;
    UUID projectId;
}
