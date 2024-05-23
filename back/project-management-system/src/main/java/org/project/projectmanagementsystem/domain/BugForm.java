package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.UUID;

@With
@Value
@Builder
public class BugForm {
    String title;
    String description;
    UUID projectId;
    UUID taskCode;
    String username;
    Bug.BugType bugType;
}
