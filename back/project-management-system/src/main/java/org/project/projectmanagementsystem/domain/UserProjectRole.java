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

    public static UserProjectRole buildUserProjectRole(User user, Project savedProject, Role role) {
        return UserProjectRole.builder()
                .user(user)
                .project(savedProject)
                .role(role)
                .build();
    }
}
