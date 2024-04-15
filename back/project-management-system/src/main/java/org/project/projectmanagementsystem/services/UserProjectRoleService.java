package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.UserProjectRoleRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.user.UserProjectRoleNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProjectRoleService {
    private final UserProjectRoleRepository userProjectRoleRepository;

    public UserProjectRole addUserProjectRole(User user, Project savedProject, Role role) {
        UserProjectRole userProjectRoleEntityToSave = UserProjectRole.buildUserProjectRole(user, savedProject, role);

        return userProjectRoleRepository.addUserProjectRole(userProjectRoleEntityToSave);
    }

    public List<UserProjectRole> findUsersUnassignedToProject(Project project) {
        UUID projectId = project.getProjectId();

        return userProjectRoleRepository.findUsersUnassignedToProject(projectId);
    }

    public void removeUserProjectRole(Project assignProject, User user) {
        UserProjectRole userProjectRoleToRemove = findUserProjectRole(assignProject, user);
        userProjectRoleRepository.removeUserProjectRole(userProjectRoleToRemove);
    }

    private UserProjectRole findUserProjectRole(Project assignProject, User user) {
        return userProjectRoleRepository.findUserProjectRole(assignProject.getProjectId(), user.getUserId())
                .orElseThrow(() -> new UserProjectRoleNotFound("Couldn't find user role in project!", HttpStatus.CONFLICT));
    }

    public List<UserProjectRole> findAllUserProjectsAsMember(User user) {
        return userProjectRoleRepository.findAllUserProjectsAsMember(user.getEmail());
    }
}
