package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.project.projectmanagementsystem.api.dto.ProjectTaskStatusCount;

import java.time.OffsetDateTime;
import java.util.List;

@With
@Value
@Builder
public class ProjectSummary {
    Project project;
    OffsetDateTime createdDate;
    List<User> usersInProject;
    List<Bug> bugsInProject;
    List<ProjectTaskStatusCount> projectTaskStatusCounts;
    List<UserTasks> usersCountFinishedTasks;
}
