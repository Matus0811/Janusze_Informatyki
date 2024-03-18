package com.project.projectmanagementsystem.domain;

import com.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import lombok.*;

import java.util.Set;

@With
@Data
@Value
@Builder
@EqualsAndHashCode
public class Role {
    Long roleId;
    String name;
    Set<UserProjectRole> userProjectRoles;
}
