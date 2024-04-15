package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.UserTaskRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserTask;
import org.project.projectmanagementsystem.services.exceptions.task.UserTaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTaskService {
    private final UserTaskRepository userTaskRepository;
    public UserTask assignUserToTask(Task task, User userToAssign) {
        UserTask userTask = UserTask.builder()
                .task(task)
                .user(userToAssign)
                .finished(false)
                .build();

        return userTaskRepository.save(userTask);
    }

    public UserTask findUserTask(Task task, User user){
        return userTaskRepository.findUserTask(task.getTaskId(), user.getUserId())
                .orElseThrow(() -> new UserTaskNotFoundException("Couldn't find task: [%s] for user: [%s]".formatted(
                        task.getTaskCode(), user.getEmail()
                ), HttpStatus.NOT_FOUND));
    }

    public void setUserTaskToFinished(Task finishedUserTask, User user) {
        UserTask userTask = findUserTask(finishedUserTask,user);

        userTask = userTask.withFinished(true);

        userTaskRepository.save(userTask);
    }

    public List<UserTask> findUsersWhoAreWorkingAtTask(Task task, UUID projectId) {
        return userTaskRepository.findNotFinished(task.getTaskCode(), projectId);
    }

    public void removeUserAssignedToTasks(Project project, User user) {
        List<UserTask> userTasks = findAllUserTasksAssignedToUserInProject(user,project);

        userTasks.forEach(userTaskRepository::remove);
    }

    private List<UserTask> findAllUserTasksAssignedToUserInProject(User user, Project project) {
        return userTaskRepository.findAllUserTasksAssignedToUserInProject(user.getUserId(),project.getProjectId());
    }
}
