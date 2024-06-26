package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;
import java.util.UUID;

@With
@Value
@Builder
public class AssignForm {
    String taskCode;
    List<String> userEmails;
    UUID projectId;
}
