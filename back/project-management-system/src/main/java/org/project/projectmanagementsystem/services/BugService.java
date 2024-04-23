package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.BugRepository;
import org.project.projectmanagementsystem.domain.*;
import org.springframework.stereotype.Service;

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

        projectService.updateProjectStatus(projectWithBug, Project.ProjectStatus.BUG_FOUND);

        Bug bugToCreate = Bug.buildBug(bugForm,reporter,projectWithBug);
        Task bugTask = Task.buildBugTask(bugToCreate);

        taskService.createTask(bugTask);

        return bugRepository.save(bugToCreate);
    }
}
