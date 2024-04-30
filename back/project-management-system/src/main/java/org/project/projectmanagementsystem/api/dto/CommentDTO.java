package org.project.projectmanagementsystem.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;

@With
@Value
@Builder
public class CommentDTO {
    Long commentId;
    String text;
    OffsetDateTime date;
    String username;
}
