package org.project.projectmanagementsystem.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Value
@Builder
public class Comment {
    Long commentId;
    String text;
    OffsetDateTime date;
    User user;
    Task task;

    public static Comment buildComment(String commentText, Task task, User user) {
        return Comment.builder()
                .text(commentText)
                .date(OffsetDateTime.now())
                .user(user)
                .task(task)
                .build();
    }
}
