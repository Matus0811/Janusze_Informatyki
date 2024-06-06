package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectAlreadyExistsException;
import org.project.projectmanagementsystem.services.exceptions.user.UserAssignToProjectException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectUserService {
    private final UserService userService;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final UserProjectRoleService userProjectRoleService;
    private final TaskUserService taskUserService;
    private final CommentService commentService;

    @Transactional
    public void addUsersToProject(UUID projectId, List<String> usernames) {
        List<User> usersToAssignToProject = userService.findUsersWithGivenUsernames(usernames);
        if (usersToAssignToProject.isEmpty()) {
            throw new UserAssignToProjectException("Cannot assign users to project, list is empty!", HttpStatus.CONFLICT);
        }

        Project project = projectService.findById(projectId);
        Role assignUserRole = roleService.findRoleByName("TEAM_MEMBER");

        usersToAssignToProject.forEach(this::checkDefaultRole);

        usersToAssignToProject.forEach(
                user -> userProjectRoleService.addUserProjectRole(user, project, assignUserRole)
        );
    }

    @Transactional
    public List<User> getUnassignedUsers(UUID projectId, String username, Pageable pageable) {
        Project project = projectService.findById(projectId);
        return userService.findUnassignedUsersToProject(project, username, pageable).stream()
                .distinct()
                .toList();
    }

    @Transactional
    public void removeUserFromProject(UUID projectId, String email) {
        taskUserService.removeUserAssignedToTasks(projectId, email);
        userProjectRoleService.removeUserProjectRole(projectId, email);
        commentService.removeUserCommentsInProject(email, projectId);
    }

    public List<Project> findAllUserProjectsAsMember(String userEmail, Pageable pageable) {
        return userProjectRoleService.findAllUserProjectsAsMember(userEmail, pageable)
                .stream()
                .map(UserProjectRole::getProject)
                .toList();
    }

    @Transactional
    public Project processProjectCreation(ProjectForm projectForm) {
        Project projectToCreate = Project.buildProjectFromForm(projectForm);

        if (userProjectRoleService.existProjectName(projectForm.getName(), projectForm.getEmail())) {
            throw new ProjectAlreadyExistsException("Projekt o nazwie [%s] już istnieje!".formatted(projectToCreate.getName()), HttpStatus.CONFLICT);
        }
        User owner = userService.findByEmail(projectForm.getEmail());

        checkDefaultRole(owner);

        return projectService.createProject(projectToCreate, owner);
    }

    private void checkDefaultRole(User owner) {
        if (userProjectRoleService.findAllUserProjectsAsOwner(owner.getEmail()).isEmpty()) {
            userProjectRoleService.removeDefaultUserRole(owner.getUserId());
        }
    }

    public List<User> findPagedProjectMembers(UUID projectId, Pageable pageable) {
        return userProjectRoleService.findPagedProjectMembers(projectId, pageable);
    }

    public Long countProjectMembers(UUID projectId) {
        return userProjectRoleService.countProjectMembers(projectId);
    }
}
