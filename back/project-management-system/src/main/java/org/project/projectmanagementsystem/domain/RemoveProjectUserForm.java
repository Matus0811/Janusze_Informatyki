package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.UUID;

@With
@Value
@Builder
public class RemoveProjectUserForm {
    UUID projectId;
    String memberEmailToRemove;
}
