package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class TaskForm {
    String description;
    String status;
    String priority;
    String startDate;
    String finishDate;
    String projectId;
}
