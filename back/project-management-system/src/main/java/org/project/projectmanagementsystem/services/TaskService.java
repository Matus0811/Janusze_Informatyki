package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.TaskRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.task.TaskException;
import org.project.projectmanagementsystem.services.exceptions.task.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ProjectService projectService;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserTaskService userTaskService;

    @Transactional
    public Task processTaskCreation(TaskForm taskForm) {
        Project project = projectService.findById(UUID.fromString(taskForm.getProjectId()));
        project = project.withProjectStatus(Project.ProjectStatus.IN_PROGRESS);

        projectService.updateProjectStatus(project);
        Task task = Task.buildTaskFromTaskForm(taskForm,project);

        return taskRepository.addTask(task);
    }

    @Transactional
    public void addUserToTask(AssignForm assignForm) {
        Optional<Task> foundTask = taskRepository.findByTaskCode(assignForm.getTaskCode());

        if(foundTask.isEmpty()){
            throw new TaskNotFoundException("Given task not found", HttpStatus.NOT_FOUND);
        }

        Task task = foundTask.get();

        if(task.getStatus() == Task.TaskStatus.FINISHED){
            throw new TaskException("Task is finished, cannot add user", HttpStatus.CONFLICT);
        }

        User userToAssign = userService.findByEmail(assignForm.getUserEmails().get(0));

        userTaskService.assignUserToTask(task,userToAssign);
    }
}
