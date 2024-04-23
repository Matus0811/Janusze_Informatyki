package org.project.projectmanagementsystem.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.TaskForm;
import org.springframework.stereotype.Service;

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

        projectService.updateProjectStatus(project, Project.ProjectStatus.IN_PROGRESS);
        return createdTask;
    }
}
