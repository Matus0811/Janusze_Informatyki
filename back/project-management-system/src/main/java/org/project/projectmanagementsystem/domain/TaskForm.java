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
    String description;
    String status;
    String priority;
    OffsetDateTime startDate;
    OffsetDateTime finishDate;
    UUID projectId;
}
