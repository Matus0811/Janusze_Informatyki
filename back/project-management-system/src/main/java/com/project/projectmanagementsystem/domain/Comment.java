package com.project.projectmanagementsystem.domain;

import com.project.projectmanagementsystem.database.entities.TaskEntity;
import com.project.projectmanagementsystem.database.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@With
@Data
@Value
@Builder
@EqualsAndHashCode
public class Comment {
    Long commentId;
    String text;
    OffsetDateTime date;
    UserEntity user;
    TaskEntity task;
}