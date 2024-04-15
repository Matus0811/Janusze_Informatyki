package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.TaskDTO;
import org.project.projectmanagementsystem.api.dto.TaskFormDTO;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.TaskMapper;
import org.project.projectmanagementsystem.services.ProjectService;
import org.project.projectmanagementsystem.services.TaskService;
import org.project.projectmanagementsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskFormDTO taskFormDTO) {
        Project project = projectService.findById(taskFormDTO.projectId());

        TaskDTO taskDTO = TaskMapper.INSTANCE.mapFromDomainToDto(
                taskService.processTaskCreation(
                        TaskMapper.INSTANCE.mapFromDtoToDomain(taskFormDTO),
                        project
                )
        );

        projectService.updateProjectStatus(project, Project.ProjectStatus.IN_PROGRESS);

        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{taskCode}/add-user")
    public ResponseEntity<?> addUsersToTask(
            @PathVariable("taskCode") String taskCode,
            @RequestBody List<String> userEmails) {
        List<User> usersToAssignToTask = userService.findUsersByEmail(userEmails);

        taskService.addUsersToTask(taskCode, usersToAssignToTask);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{taskCode}/users/finish-task")
    public ResponseEntity<?> finishTaskByMember(
            @PathVariable("taskCode") String taskCode,
            @RequestParam("email") String email
    ) {
        User user = userService.findByEmail(email);
        taskService.finishTaskByMember(taskCode, user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{taskCode}/finish")
    public ResponseEntity<?> finishTaskByOwner(
            @PathVariable("taskCode") String taskCode,
            @RequestParam(name = "project") UUID projectId
    ) {
        Project assignedProject = projectService.findById(projectId);

        taskService.finishTask(taskCode, assignedProject);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
