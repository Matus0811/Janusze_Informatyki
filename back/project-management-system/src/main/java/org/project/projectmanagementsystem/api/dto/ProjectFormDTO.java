package org.project.projectmanagementsystem.api.dto;

import java.time.OffsetDateTime;

public record ProjectFormDTO(String email, String name, String description, OffsetDateTime finishDate) {

}
