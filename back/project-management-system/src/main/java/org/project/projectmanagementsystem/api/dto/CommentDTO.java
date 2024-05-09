package org.project.projectmanagementsystem.api.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    Long commentId;
    String text;
    OffsetDateTime date;
    String username;
}
