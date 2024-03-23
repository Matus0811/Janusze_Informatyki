package org.project.projectmanagementsystem.domain;

import lombok.*;

@With
@Value
@Builder
public class UserProjectRole {
    Long userProjectRoleId;
    User user;
    Project project;
    Role role;
}
