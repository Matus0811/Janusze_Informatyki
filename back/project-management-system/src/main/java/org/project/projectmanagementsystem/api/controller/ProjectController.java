package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.ProjectDTO;
import org.project.projectmanagementsystem.api.dto.ProjectFormDTO;
import org.project.projectmanagementsystem.domain.mapper.ProjectFormMapper;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.project.projectmanagementsystem.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectFormDTO project){
        ProjectDTO createdProject = ProjectMapper.INSTANCE.mapFromDomainToDto(
                projectService.processProjectCreation(
                        ProjectFormMapper.INSTANCE.mapToDomain(project)
                )
        );

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }
}
