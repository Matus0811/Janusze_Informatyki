package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.TaskDTO;
import org.project.projectmanagementsystem.api.dto.TaskFormDTO;
import org.project.projectmanagementsystem.domain.mapper.TaskMapper;
import org.project.projectmanagementsystem.services.BugService;
import org.project.projectmanagementsystem.services.ProjectTaskService;
import org.project.projectmanagementsystem.services.TaskService;
import org.project.projectmanagementsystem.services.TaskUserService;
import org.project.projectmanagementsystem.util.TaskUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ProjectTaskService projectTaskService;
    private final TaskUserService taskUserService;


    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskFormDTO taskFormDTO) {
        TaskDTO taskDTO = TaskMapper.INSTANCE.mapFromDomainToDto(
                projectTaskService.processProjectTaskCreation(
                        TaskMapper.INSTANCE.mapFromDtoToDomain(taskFormDTO)
                )
        );
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getPagedTasksForProject(
            @RequestParam("projectId") UUID projectId,
            @RequestParam("page") Integer page,
            @RequestParam("taskType") String type
    ) {
        Pageable pageable = PageRequest.of(page, 6).withSort(Sort.by("startDate").descending());
        List<TaskDTO> pagedTasksForProject = taskService.findPagedProjectTasksWithStatus(
                        projectId,
                        TaskUtils.taskStatuses(type),
                        pageable).stream()
                .map(TaskMapper.INSTANCE::mapFromDomainToDto).toList();

        return new ResponseEntity<>(pagedTasksForProject,HttpStatus.OK);
    }

    @PatchMapping("/{taskCode}/add-users")
    public ResponseEntity<?> addUsersToTask(
            @PathVariable("taskCode") String taskCode,
            @RequestBody List<String> userEmails) {

        taskUserService.addUsersToTask(taskCode, userEmails);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{taskCode}/users/finish-task")
    public ResponseEntity<?> finishTaskByMember(
            @PathVariable("taskCode") String taskCode,
            @RequestParam("email") String email
    ) {
        taskUserService.finishTaskByMember(taskCode, email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{taskCode}/finish")
    public ResponseEntity<?> finishTaskByOwner(
            @PathVariable("taskCode") String taskCode,
            @RequestParam(name = "project") UUID projectId
    ) {
        taskUserService.finishTaskByOwner(taskCode, projectId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{taskCode}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable("taskCode") String taskCode) {
        taskService.deleteTask(taskCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
