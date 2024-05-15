package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.BugRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.BugNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class BugService {
    private final BugRepository bugRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;

    @Transactional
    public Bug createBug(BugForm bugForm) {
        Project projectWithBug = projectService.findById(bugForm.getProjectId());
        User reporter = userService.findByEmail(bugForm.getUserEmail());

        Task bugTask = Task.buildBugTask();
        Bug bugToCreate = Bug.buildBug(bugForm,reporter,projectWithBug,bugTask);

        bugTask = bugTask.withName(bugToCreate.getTitle())
                .withDescription(bugToCreate.getDescription())
                .withProject(projectWithBug);

        Task createdTask = taskService.createTask(bugTask);

        bugToCreate = bugToCreate.withTask(createdTask);

        return bugRepository.save(bugToCreate);
    }


    public Bug findBugForTask(Task task) {
        return bugRepository.findBugForTask(task)
                .orElseThrow(() -> new BugNotFoundException(
                        "Bug with project [%s] not found".formatted(task.getProject().getProjectId()), HttpStatus.NOT_FOUND)
                );
    }

    public void finishBugForTask(Task task) {
        Bug bugToFinish = findBugForTask(task);
        bugToFinish = bugToFinish.withFixedDate(OffsetDateTime.now());

        bugRepository.save(bugToFinish);
    }
}
