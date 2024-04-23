package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.database.ProjectRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.services.exceptions.project.ActiveProjectLimitException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectDeleteException;
import org.project.projectmanagementsystem.services.exceptions.project.ProjectNotFoundException;
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
    private final RoleService roleService;
    private final UserProjectRoleService userProjectRoleService;
    private final TaskService taskService;


    public Project createProject(Project projectToCreate, User owner) {
        List<Project> activeUserProjects = findNotFinishedOwnerProjects(owner.getEmail());

        if (activeUserProjects.size() == 10) {
            log.error("Error during creating project, reached maximum number of active projects [{}]", activeUserProjects);
            throw new ActiveProjectLimitException("Possible maximum active projects is 10!",HttpStatus.CONFLICT);
        }

        Role role = roleService.findRoleByName("PROJECT_OWNER");

        Project savedProject = projectRepository.addProject(projectToCreate);
        userProjectRoleService.addUserProjectRole(owner, savedProject, role);
        return savedProject;
    }

    public List<Project> findNotFinishedOwnerProjects(String ownerEmail) {
        return projectRepository.findNotFinishedUserProjects(ownerEmail);
    }

    public boolean projectExists(String projectName) {
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
