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
                .stream()
                .map(UserProjectRole::getRole)
                .distinct()
                .collect(Collectors.toList());
    }

    public void removeDefaultUserRole(Long userId) {
        userProjectRoleRepository.removeDefaultUserRole(userId);
    }

    public List<User> findPagedProjectMembers(UUID projectId, Pageable pageable) {
        return userProjectRoleRepository.findPagedProjectMembers(projectId,pageable)
                .stream()
                .map(UserProjectRole::getUser)
                .toList();
    }

    public List<UserProjectRole> findPagedProjectMembersWithGivenUsernameNotIncludeUsersIdsInCurrentTask(
            UUID projectId,
            String username,
            Pageable pageable,
            List<Long> allAssignedUserToTaskIds
    ) {
        return userProjectRoleRepository.findPagedProjectMembersWithGivenUsernameNotIncludeUsersIdsInCurrentTask(
                 projectId,
                 username,
                 pageable,
                allAssignedUserToTaskIds
        );
    }

    public Long countProjectMembers(UUID projectId) {
        return userProjectRoleRepository.countProjectMembers(projectId);
    }

    public boolean existProjectName(String name, String email) {
        return userProjectRoleRepository.findOwnerProjectName(name, email).isPresent();
    }
}
