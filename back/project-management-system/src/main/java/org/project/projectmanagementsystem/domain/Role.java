package org.project.projectmanagementsystem.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
public class Role {
    Long roleId;
    String name;
    Set<UserProjectRole> userProjectRoles;
}
