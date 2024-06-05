package org.project.projectmanagementsystem.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.util.TaskUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectTaskService {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserProjectRoleService userProjectRoleService;
    private final TaskUserService taskUserService;


    @Transactional
    public Task processProjectTaskCreation(TaskForm taskForm) {
        Project project = projectService.findById(taskForm.getProjectId());

        Task taskToCreate = Task.buildTaskFromTaskForm(taskForm,project);

        if(findAllTasksAssignedToProject(project).isEmpty()){
            projectService.updateProjectStatus(project, Project.ProjectStatus.IN_PROGRESS);
        }

        return taskService.createTask(taskToCreate);
    }

    public List<Task> findAllTasksAssignedToProject(Project project){
        return taskService.findPagedProjectTasksWithStatus(
                project.getProjectId(),
                TaskUtils.taskStatuses("ALL"),
                Pageable.unpaged()
        );
    }

    public List<User> findUsersInProjectNotAssignedToTaskWithUsername(UUID projectId, String taskCode, String username, Pageable pageable) {
        List<Long> allAssignedUserToTaskIds = taskUserService.findAllUsersAssignedToTask(taskCode).stream()
                .map(UserTask::getUser)
                .map(User::getUserId)
                .toList();

        return userProjectRoleService.findPagedProjectMembersWithGivenUsernameNotIncludeUsersIdsInCurrentTask(projectId, username, pageable, allAssignedUserToTaskIds)
                .stream()
                .map(UserProjectRole::getUser)
                .toList();
    }
}
