package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;

@With
@Value
@Builder
public class ProjectForm {
    String email;
    String name;
    String description;
    OffsetDateTime finishDate;
}
