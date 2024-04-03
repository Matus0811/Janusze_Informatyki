package org.project.projectmanagementsystem.services;

import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.springframework.stereotype.Service;

@Service
public class UserProjectRoleService {

    public UserProjectRole createUserProjectRole(Project project, User user, String roleName){
        return UserProjectRole.builder()
                .user(user)
                .project(project)
                .role(Role.builder()
                        .name(roleName)
                        .build())
                .build();
    }
}
