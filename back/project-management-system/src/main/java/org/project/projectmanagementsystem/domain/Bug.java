package org.project.projectmanagementsystem.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Value
@Builder
public class Bug {

    Long bugId;
    String serialNumber;
    String title;
    String description;
    Project project;
    User user;
    Bug.BugType bugType;
    OffsetDateTime reportDate;
    Boolean fixed;

    public enum BugType {
        LOGICAL, SECURITY, SYNTAX, COMMUNICATION, CALCULATION, FUNCTIONAL
    }
}
