package com.project.projectmanagementsystem.domain;

import lombok.*;

@With
@Data
@Value
@Builder
@EqualsAndHashCode
public class UserProjectRole {
    Long userProjectRoleId;
    User user;
    Project project;
    Role role;
}
