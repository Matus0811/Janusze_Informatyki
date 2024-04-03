package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.database.dao.ProjectDAO;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.project.ActiveProjectLimitException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectDAO projectDAO;
    private final UserService userService;
    private final RoleService roleService;

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

        Project createdProject = buildProjectFromForm(projectForm);
        Role role = roleService.findRoleByName("PROJECT_OWNER");

        return projectDAO.addProject(createdProject, owner, role);
    }

    private Project buildProjectFromForm(ProjectForm projectForm) {
        Project project = Project.builder()
                .name(projectForm.getName())
                .projectStatus(Project.ProjectStatus.INITIAL)
                .description(projectForm.getDescription())
                .startDate(OffsetDateTime.now())
                .build();

        if (!projectForm.getFinishDate().isBlank()) {
            project = project.withFinishDate(OffsetDateTime.parse(projectForm.getFinishDate()));
        }

        return project;
    }

    public List<Project> findNotFinishedUserProjects(User owner) {
        return projectDAO.findNotFinishedUserProjects(owner);
    }

    private boolean projectExists(String projectName) {
        return projectDAO.findByName(projectName).isPresent();
    }
}
