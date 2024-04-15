package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.TaskRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.task.TaskException;
import org.project.projectmanagementsystem.services.exceptions.task.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserTaskService userTaskService;

    @Transactional
    public Task processTaskCreation(TaskForm taskForm, Project project) {
        Task task = Task.buildTaskFromTaskForm(taskForm,project);

        return taskRepository.addTask(task);
    }

    public Task findByTaskCode(String taskCode){
        return taskRepository.findByTaskCode(taskCode)
                .orElseThrow(() -> new TaskNotFoundException("Given task not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void addUsersToTask(String taskCode, List<User> usersToAssign) {
        Task task = findByTaskCode(taskCode);

        if(task.getStatus() == Task.TaskStatus.FINISHED){
            throw new TaskException("Task is finished, cannot add user", HttpStatus.CONFLICT);
        }

        usersToAssign.forEach(userToAssign -> userTaskService.assignUserToTask(task,userToAssign));
    }

    @Transactional
    public void finishTaskByMember(String taskCode, User user) {
        Task finishedUserTask = findByTaskCode(taskCode);
        userTaskService.setUserTaskToFinished(finishedUserTask,user);
    }

    @Transactional
    public void finishTask(String taskCode, Project assignProject) {
        Task task = findByTaskCode(taskCode);

        int numberOfUsersWorkingOnTask = userTaskService
                .findUsersWhoAreWorkingAtTask(task,assignProject.getProjectId())
                .size();

        if(numberOfUsersWorkingOnTask > 0) {
            throw new TaskException("There are still [%s] users who are working on given task".formatted(numberOfUsersWorkingOnTask),HttpStatus.CONFLICT);
        }

        task = task.withStatus(Task.TaskStatus.FINISHED).withFinishDate(OffsetDateTime.now());
        taskRepository.save(task);
    }

    public List<Task> findProjectTasksWithStatus(UUID projectId, EnumSet<Task.TaskStatus> taskStatuses) {
        return taskRepository.findProjectTasksWithStatus(projectId,taskStatuses);
    }
}
