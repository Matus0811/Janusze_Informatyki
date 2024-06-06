package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.ProjectTaskStatusCount;
import org.project.projectmanagementsystem.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectSummaryService {
    private final ProjectService projectService;
    private final UserService userService;
    private final BugService bugService;
    private final TaskService taskService;

    public ProjectSummary generateSummaryForProject(UUID projectId){
        Project project = projectService.findById(projectId);
        User owner = userService.findProjectOwner(projectId);
        List<ProjectTaskStatusCount> allProjectTasksGrouped = taskService.findAllProjectTasksGrouped(projectId);
        List<Bug> bugsForProject = bugService.findBugsForProject(projectId, Pageable.unpaged());
        List<User> usersAssignedToProject = userService.findUsersAssignedToProject(projectId);
        List<UserTasks> usersCountFinishedTasks = usersAssignedToProject.stream().map(user -> {
            Long numberOfUserRealizedTasks = taskService.countFinishedUserTasksForProject(user, projectId);
            return UserTasks.builder().username(user.getUsername()).numberOfRealizedTasks(numberOfUserRealizedTasks).build();
        }).toList();

        return ProjectSummary.builder()
                .project(project)
                .createdDate(OffsetDateTime.now())
                .projectTaskStatusCounts(allProjectTasksGrouped)
                .bugsInProject(bugsForProject)
                .usersInProject(usersAssignedToProject)
                .usersCountFinishedTasks(usersCountFinishedTasks)
                .projectOwner(owner)
                .build();
    }
}
