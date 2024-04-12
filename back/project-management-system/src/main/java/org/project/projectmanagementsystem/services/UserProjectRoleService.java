package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.UserProjectRoleRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProjectRoleService {
    private final UserProjectRoleRepository userProjectRoleRepository;

    public UserProjectRole addUserProjectRole(User user, Project savedProject, Role role) {
        UserProjectRole userProjectRoleEntityToSave = UserProjectRole.builder()
                .user(user)
                .project(savedProject)
                .role(role)
                .build();
        return userProjectRoleRepository.addUserProjectRole(userProjectRoleEntityToSave);
    }

    public List<UserProjectRole> findUsersUnassignedToProject(Project project) {
        UUID projectId = project.getProjectId();

        return userProjectRoleRepository.findUsersUnassignedToProject(projectId);
    }
}
