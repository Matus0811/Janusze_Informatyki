package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.UserDTO;
import org.project.projectmanagementsystem.database.UserTaskRepository;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserTask;
import org.project.projectmanagementsystem.services.exceptions.task.TaskException;
import org.project.projectmanagementsystem.services.exceptions.task.UserTaskNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskUserService {
    private final UserTaskRepository userTaskRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final BugService bugService;
    public UserTask findUserTask(String taskCode, String userEmail) {
        return userTaskRepository.findUserTask(taskCode, userEmail)
                .orElseThrow(() -> new UserTaskNotFoundException("Couldn't find task: [%s] for user: [%s]".formatted(
                        taskCode, userEmail
                ), HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void finishTaskByMember(String taskCode, String userEmail, UUID projectId) {
        UserTask userTask = findUserTask(taskCode, userEmail);

        userTask = userTask.withFinished(true);

        userTaskRepository.save(userTask);

        finishWholeTask(taskCode, projectId);
    }

    private void finishWholeTask(String taskCode, UUID projectId) {
        Task task = taskService.findByTaskCode(taskCode);

        int numberOfUsersWorkingOnTask = findUsersWhoAreWorkingAtTask(taskCode, projectId).size();

        if (numberOfUsersWorkingOnTask == 0) {
            if(Task.TaskStatus.BUG == task.getStatus()){
                bugService.finishBugForProject(task.getProject());
            }
            task = task.withStatus(Task.TaskStatus.FINISHED).withFinishDate(OffsetDateTime.now());
            taskService.save(task);
        }
    }

    public List<UserTask> findUsersWhoAreWorkingAtTask(String taskCode, UUID projectId) {
        return userTaskRepository.findNotFinished(taskCode, projectId);
    }

    public void removeUserAssignedToTasks(UUID projectId, String userEmail) {
        List<UserTask> userTasks = findAllUserTasksAssignedToUserInProject(userEmail, projectId);

        userTasks.forEach(userTaskRepository::remove);
    }

    private List<UserTask> findAllUserTasksAssignedToUserInProject(String userEmail, UUID projectId) {
        return userTaskRepository.findAllUserTasksAssignedToUserInProject(userEmail, projectId);
    }


    @Transactional
    public void addUsersToTask(String taskCode, List<String> usersEmail) {
        List<User> usersToAssign = userService.findUsersByEmail(usersEmail);
        Task task = taskService.findByTaskCode(taskCode);

        if (task.getStatus() == Task.TaskStatus.FINISHED) {
            throw new TaskException("Task is finished, cannot add user", HttpStatus.CONFLICT);
        }

        if(task.getStatus() != Task.TaskStatus.BUG){
            task = task.withStatus(Task.TaskStatus.IN_PROGRESS);
            taskService.save(task);
        }

        List<UserTask> taskUsers = buildTaskUserList(task, usersToAssign);
        userTaskRepository.saveAll(taskUsers);
    }

    private List<UserTask> buildTaskUserList(Task task, List<User> usersToAssign) {
        return usersToAssign.stream()
                .map(user -> UserTask.buildTaskUser(task, user))
                .collect(Collectors.toList());
    }

    public List<UserTask> findPagedUsersAssignedToTask(String taskCode, Pageable pageable) {
        return userTaskRepository.findPagedUsersAssignedToTask(taskCode,pageable);
    }

    @Transactional
    public void removeUserAssignedToTask(String taskCode, String username) {
        User assignedUser = userService.findByUsername(username);
        UserTask userTask = findUserTask(taskCode,assignedUser.getEmail());

        userTaskRepository.remove(userTask);
    }
}
