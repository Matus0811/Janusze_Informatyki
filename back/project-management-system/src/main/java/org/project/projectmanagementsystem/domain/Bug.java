package org.project.projectmanagementsystem.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@With
@Value
@Builder
public class Bug {

    Long bugId;
    String serialNumber;
    String title;
    String description;
    Project projectWithBug;
    Task taskWithBug;
    User reportedUser;
    Bug.BugType bugType;
    Bug.BugStatus bugStatus;
    OffsetDateTime reportDate;
    OffsetDateTime fixedDate;

    public static Bug buildBug(BugForm bugForm,User reporter, Project projectWithBug, Task taskWithBug) {
        return Bug.builder()
                .serialNumber(UUID.randomUUID().toString())
                .title(bugForm.getTitle())
                .description(bugForm.getDescription())
                .projectWithBug(projectWithBug)
                .reportedUser(reporter)
                .taskWithBug(taskWithBug)
                .bugStatus(BugStatus.REPORTED)
                .bugType(bugForm.getBugType())
                .reportDate(bugForm.getReportDate())
                .build();
    }

    public enum BugType {
        LOGICAL, SECURITY, SYNTAX, COMMUNICATION, CALCULATION, FUNCTIONAL
    }

    public enum BugStatus{
        REPORTED, IN_REPAIR, FIXED
    }
}
