package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.database.entities.UserEntity;

@With
@Value
@Builder
public class CommentForm {
    String text;
    String username;
    String taskCode;
}
