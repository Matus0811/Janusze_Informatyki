package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.ProjectDTO;
import org.project.projectmanagementsystem.api.dto.ProjectFormDTO;
import org.project.projectmanagementsystem.api.dto.UserDataDTO;
import org.project.projectmanagementsystem.domain.mapper.ProjectFormMapper;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.project.projectmanagementsystem.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class
ProjectController {
    private final ProjectService projectService;
    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectFormDTO project){
        ProjectDTO createdProject = ProjectMapper.INSTANCE.mapFromDomainToDto(
                projectService.processProjectCreation(
                        ProjectFormMapper.INSTANCE.mapFromDtoToDomain(project)
                )
        );

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable(name = "projectId") UUID projectId){
        projectService.removeProject(projectId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAllUserProject(@RequestBody UserDataDTO userDataDTO){
        List<ProjectDTO> userProjects = projectService
                .findNotFinishedUserProjects(UserMapper.INSTANCE.mapFromDtoToDomain(userDataDTO))
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromDomainToDto)
                .toList();
        return new ResponseEntity<>(userProjects,HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable String projectId){
        ProjectDTO projectDTO = ProjectMapper.INSTANCE.mapFromDomainToDto(projectService.findProject(projectId));

        return new ResponseEntity<>(projectDTO,HttpStatus.OK);
    }
}
