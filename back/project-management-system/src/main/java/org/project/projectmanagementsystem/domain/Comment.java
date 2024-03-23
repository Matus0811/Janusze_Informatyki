package org.project.projectmanagementsystem.domain;

import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.database.entities.UserEntity;
import lombok.*;

import java.time.OffsetDateTime;

@With
@Value
@Builder
public class Comment {
    Long commentId;
    String text;
    OffsetDateTime date;
    UserEntity user;
    TaskEntity task;
}
