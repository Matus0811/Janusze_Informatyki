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
    Project project;
    Task task;
    User user;
    Bug.BugType bugType;
    OffsetDateTime reportDate;
    OffsetDateTime fixedDate;

    public static Bug buildBug(BugForm bugForm,User reporter, Project projectWithBug, Task task) {
        return Bug.builder()
                .serialNumber(UUID.randomUUID().toString())
                .title(bugForm.getTitle())
                .description(bugForm.getDescription())
                .project(projectWithBug)
                .user(reporter)
                .bugType(bugForm.getBugType())
                .reportDate(OffsetDateTime.now())
                .build();
    }

    public enum BugType {
        LOGICAL, SECURITY, SYNTAX, COMMUNICATION, CALCULATION, FUNCTIONAL
    }
}
