package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.database.dao.ProjectDAO;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.EmptyFormException;
import org.project.projectmanagementsystem.services.exceptions.project.ActiveProjectLimitException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectAlreadyExistsException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectDeleteException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectDAO projectDAO;
    private final UserService userService;
    private final RoleService roleService;
    private final UserProjectRoleService userProjectRoleService;

    @Transactional
    public Project processProjectCreation(ProjectForm projectForm) {
        String projectName = projectForm.getName();

        if (projectExists(projectName)) {
            throw new ProjectAlreadyExistsException("Project with name [%s] already exists!".formatted(projectName));
        }

        return createProject(projectForm);
    }

    private Project createProject(ProjectForm projectForm) {
        User owner = userService.findByEmail(projectForm.getOwnerEmail());

        List<Project> activeUserProjects = findNotFinishedUserProjects(owner);

        if (activeUserProjects.size() == 10) {
            log.error("Error during creating project, reached maximum number of active projects [{}]", activeUserProjects);
            throw new ActiveProjectLimitException("Possible maximum active projects is 10!");
        }

        Project createdProject = Project.buildProjectFromForm(projectForm);
        Role role = roleService.findRoleByName("PROJECT_OWNER");

        return projectDAO.addProject(createdProject, owner, role);
    }

    public List<Project> findNotFinishedUserProjects(User owner) {
        return projectDAO.findNotFinishedUserProjects(owner);
    }

    private boolean projectExists(String projectName) {
        return projectDAO.findByName(projectName).isPresent();
    }

    public void removeProject(UUID projectId) {
        Project projectToDelete = findById(projectId);

        if (projectToDelete.getProjectStatus() == Project.ProjectStatus.FINISHED) {
            throw new ProjectDeleteException("Cannot delete project which is finished!");
        }
        projectDAO.remove(projectToDelete);
    }

    public List<Project> findNotFinishedUserProjects(UserData userData) {
        User owner;

        if (!userData.getEmail().isBlank()) {
            owner = userService.findByEmail(userData.getEmail());
        } else if (!userData.getName().isBlank()) {
            owner = userService.findByUsername(userData.getName());
        } else {
            throw new EmptyFormException("Given data is empty!");
        }
        return findNotFinishedUserProjects(owner);
    }

    public Project findById(UUID projectId) {
        return projectDAO.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException("Project with id: [%s] not found".formatted(projectId))
        );
    }

    public Project findProject(String projectId) {
        Project foundProject = findById(UUID.fromString(projectId));
        log.info("Found project: [{}]", foundProject);
        return foundProject;
    }
}
