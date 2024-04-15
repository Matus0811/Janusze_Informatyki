package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.database.ProjectRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.project.ActiveProjectLimitException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectAlreadyExistsException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectDeleteException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectNotFoundException;
import org.project.projectmanagementsystem.services.exceptions.user.UserAssignToProjectException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final UserProjectRoleService userProjectRoleService;
    private final UserTaskService userTaskService;
    private final TaskService taskService;

    @Transactional
    public Project processProjectCreation(ProjectForm projectForm) {
        String projectName = projectForm.getName();

        if (projectExists(projectName)) {
            throw new ProjectAlreadyExistsException("Project with name [%s] already exists!".formatted(projectName),HttpStatus.CONFLICT);
        }

        return createProject(projectForm);
    }

    private Project createProject(ProjectForm projectForm) {
        User owner = userService.findByEmail(projectForm.getEmail());

        List<Project> activeUserProjects = findNotFinishedOwnerProjects(owner);

        if (activeUserProjects.size() == 10) {
            log.error("Error during creating project, reached maximum number of active projects [{}]", activeUserProjects);
            throw new ActiveProjectLimitException("Possible maximum active projects is 10!",HttpStatus.CONFLICT);
        }

        Project createdProject = Project.buildProjectFromForm(projectForm);
        Role role = roleService.findRoleByName("PROJECT_OWNER");

        Project savedProject = projectRepository.addProject(createdProject);
        userProjectRoleService.addUserProjectRole(owner, savedProject, role);
        return savedProject;
    }

    public List<Project> findNotFinishedOwnerProjects(User owner) {
        return projectRepository.findNotFinishedUserProjects(owner);
    }

    private boolean projectExists(String projectName) {
        return projectRepository.findByName(projectName).isPresent();
    }

    @Transactional
    public void removeProject(UUID projectId) {
        Project projectToDelete = findById(projectId);

        if (projectToDelete.getProjectStatus() == Project.ProjectStatus.FINISHED) {
            throw new ProjectDeleteException("Cannot delete project which is finished!", HttpStatus.CONFLICT);
        }
        projectRepository.remove(projectToDelete);
    }

    public Project findById(UUID projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException("Project with id: [%s] not found".formatted(projectId),HttpStatus.NOT_FOUND)
        );
    }

    public void updateProjectStatus(Project project, Project.ProjectStatus status) {
        project = project.withProjectStatus(status);
        projectRepository.updateProjectStatus(project);
    }

    @Transactional
    public void addUsersToProject(UUID projectId, List<User> usersToAssignToProject) {
        if (usersToAssignToProject.isEmpty()) {
            throw new UserAssignToProjectException("Cannot assign users to project, list is empty!", HttpStatus.CONFLICT);
        }

        Project project = findById(projectId);
        Role assignUserRole = roleService.findRoleByName("TEAM_MEMBER");

        usersToAssignToProject.forEach(
                user -> userProjectRoleService.addUserProjectRole(user, project, assignUserRole)
        );
    }

    @Transactional
    public List<User> getUnassignedUsers(UUID projectId) {
        Project project = findById(projectId);

        return userProjectRoleService.findUsersUnassignedToProject(project).stream()
                .map(UserProjectRole::getUser)
                .distinct()
                .toList();
    }

    @Transactional
    public void removeUserFromProject(UUID projectId, User userToRemoveFromProject) {
        Project assignedProject = findById(projectId);

        userTaskService.removeUserAssignedToTasks(assignedProject,userToRemoveFromProject);
        userProjectRoleService.removeUserProjectRole(assignedProject, userToRemoveFromProject);
    }

    public List<Project> findAllUserProjectsAsMember(User user) {
        return userProjectRoleService.findAllUserProjectsAsMember(user)
                .stream()
                .map(UserProjectRole::getProject)
                .toList();
    }

    @Transactional
    public void processProjectFinishing(UUID projectId) {
        Project finishedProject = findById(projectId);

        int numberOfUnfinishedTasks = taskService.findProjectTasksWithStatus(
                finishedProject.getProjectId(),
                EnumSet.of(Task.TaskStatus.BUG,Task.TaskStatus.IN_PROGRESS, Task.TaskStatus.TO_DO)
        ).size();

        if(numberOfUnfinishedTasks > 0 ){
            throw new ProjectDeleteException("Cannot finish tasks! There are still [%s] tasks in progress"
                    .formatted(numberOfUnfinishedTasks),HttpStatus.CONFLICT);
        }

        finishedProject = finishedProject
                .withProjectStatus(Project.ProjectStatus.FINISHED)
                .withFinishDate(OffsetDateTime.now());

        projectRepository.save(finishedProject);
    }
}
