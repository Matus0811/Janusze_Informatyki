package com.project.projectmanagementsystem.domain;

import lombok.*;

@With
@Value
@Builder
public class UserTask {
    Long userTaskId;
    User user;
    Task task;
    Boolean finished;
}
