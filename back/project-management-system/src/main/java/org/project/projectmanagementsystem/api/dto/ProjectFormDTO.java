package org.project.projectmanagementsystem.api.dto;

import jakarta.validation.constraints.NotNull;


public record ProjectFormDTO(@NotNull String ownerEmail, String name, String description, String finishDate) {

}
