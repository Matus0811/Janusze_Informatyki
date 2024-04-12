package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.UserTaskRepository;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserTask;
import org.springframework.stereotype.Service;

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
}
