package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.UserProjectRoleRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.user.UserProjectRoleNotFound;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProjectRoleService {
    private final UserProjectRoleRepository userProjectRoleRepository;

    public UserProjectRole addUserProjectRole(User user, Project savedProject, Role role) {
        UserProjectRole userProjectRoleEntityToSave = UserProjectRole.buildUserProjectRole(user, savedProject, role);

        return userProjectRoleRepository.addUserProjectRole(userProjectRoleEntityToSave);
    }

    public List<UserProjectRole> findUnassignedUsersToProjectWhereUsernameStartsWith(Project project, String word) {
        UUID projectId = project.getProjectId();

        return userProjectRoleRepository.findUnassignedUsersToProjectWhereUsernameStartsWith(projectId,word);
    }

    public void removeUserProjectRole(UUID projectId, String userEmail) {
        UserProjectRole userProjectRoleToRemove = findUserProjectRole(projectId, userEmail);
        userProjectRoleRepository.removeUserProjectRole(userProjectRoleToRemove);
    }

    private UserProjectRole findUserProjectRole(UUID projectId, String userEmail) {
        return userProjectRoleRepository.findUserProjectRole(projectId,userEmail)
                .orElseThrow(() -> new UserProjectRoleNotFound("Couldn't find user role in project!", HttpStatus.CONFLICT));
    }

    public List<UserProjectRole> findAllUserProjectsAsMember(String userEmail, Pageable pageable) {
        return userProjectRoleRepository.findAllUserProjectsAsMember(userEmail,pageable);
    }

    public List<UserProjectRole> findAllUserProjectsAsOwner(String email){
        return userProjectRoleRepository.findAllUserProjectsAsOwner(email);
    }

    public List<Role> findAllUserRoles(User loggedUser) {
        return userProjectRoleRepository.findAllUserRoles(loggedUser.getUserId())
                .stream().map(UserProjectRole::getRole)
                .collect(Collectors.toList());
    }

    public void removeDefaultUserRole(Long userId) {
        userProjectRoleRepository.removeDefaultUserRole(userId);
    }
}
