package org.project.projectmanagementsystem.domain;

import lombok.*;

@With
@Value
@Builder
public class UserTask {
    Long userTaskId;
    User user;
    Task task;
    Boolean finished;

    public static UserTask buildTaskUser(Task task, User userToAssign) {
        return UserTask.builder()
                .task(task)
                .user(userToAssign)
                .finished(false)
                .build();
    }
}
