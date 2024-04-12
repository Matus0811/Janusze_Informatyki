package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.AssignFormDTO;
import org.project.projectmanagementsystem.api.dto.TaskDTO;
import org.project.projectmanagementsystem.api.dto.TaskFormDTO;
import org.project.projectmanagementsystem.domain.mapper.FormMapper;
import org.project.projectmanagementsystem.domain.mapper.TaskMapper;
import org.project.projectmanagementsystem.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskFormDTO taskFormDTO){
        TaskDTO taskDTO = TaskMapper.INSTANCE.mapFromDomainToDto(
                taskService.processTaskCreation(TaskMapper.INSTANCE.mapFromDtoToDomain(taskFormDTO))
        );
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/add-user")
    public ResponseEntity<?> addUserToTask(AssignFormDTO assignFormDTO){
        taskService.addUserToTask(FormMapper.INSTANCE.mapFromDtoToDomain(assignFormDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
