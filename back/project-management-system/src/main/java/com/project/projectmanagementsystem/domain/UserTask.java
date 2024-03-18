package com.project.projectmanagementsystem.domain;

import lombok.*;

@With
@Data
@Value
@Builder
@EqualsAndHashCode
public class UserTask {
    Long userTaskId;
    User user;
    Task task;
    Boolean finished;
}
