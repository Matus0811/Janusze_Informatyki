package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.BugRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.BugNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

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

        // status tylko zmieniany jezeli projekt jest ukończony
        if(Project.ProjectStatus.FINISHED == projectWithBug.getProjectStatus()){
            projectWithBug = projectWithBug.withFinishDate(null);
            projectService.updateProjectStatus(projectWithBug, Project.ProjectStatus.BUG_FOUND);
        }

        Bug bugToCreate = Bug.buildBug(bugForm,reporter,projectWithBug);
        Task bugTask = Task.buildBugTask(bugToCreate);

        taskService.createTask(bugTask);

        return bugRepository.save(bugToCreate);
    }


    public Bug findBugWithProject(UUID projectId) {
        return bugRepository.findBugWithProjectId(projectId)
                .orElseThrow(() -> new BugNotFoundException(
                        "Bug with project [%s] not found".formatted(projectId), HttpStatus.NOT_FOUND)
                );
    }

    public void finishBugForProject(Project project) {
        Bug bugToFinish = findBugWithProject(project.getProjectId());
        bugToFinish = bugToFinish.withFixedDate(OffsetDateTime.now());

        bugRepository.save(bugToFinish);
    }
}
