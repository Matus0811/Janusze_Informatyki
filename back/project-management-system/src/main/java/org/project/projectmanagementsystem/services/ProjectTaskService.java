package org.project.projectmanagementsystem.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.TaskForm;
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


    @Transactional
    public Task processProjectTaskCreation(TaskForm taskForm) {
        Project project = projectService.findById(taskForm.getProjectId());

        Task taskToCreate = Task.buildTaskFromTaskForm(taskForm,project);

        Task createdTask = taskService.createTask(taskToCreate);

        if(findAllTasksAssignedToProject(project).isEmpty()){
            projectService.updateProjectStatus(project, Project.ProjectStatus.IN_PROGRESS);
        }

        return createdTask;
    }

    public List<Task> findAllTasksAssignedToProject(Project project){
        return taskService.findPagedProjectTasksWithStatus(
                project.getProjectId(),
                TaskUtils.taskStatuses("ALL"),
                Pageable.unpaged()
        );
    }

}
