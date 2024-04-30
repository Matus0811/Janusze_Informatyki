package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.CommentDTO;
import org.project.projectmanagementsystem.api.dto.CommentFormDTO;
import org.project.projectmanagementsystem.domain.Comment;
import org.project.projectmanagementsystem.domain.mapper.CommentMapper;
import org.project.projectmanagementsystem.services.CommentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentFormDTO commentFormDTO){
        Comment comment = commentService.processCommentCreation(CommentMapper.INSTANCE.mapFromDtoToDomain(commentFormDTO));

        CommentDTO commentDTO = buildCommentDTO(comment);

        return new ResponseEntity<>(commentDTO,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getPagedCommentsForTask(
            @RequestParam(name="page") Integer page,
            @RequestParam(name = "taskCode") String taskCode
            ){
        Pageable pageable = PageRequest.of(page,6).withSort(Sort.by("date"));

        List<CommentDTO> pagedCommentsForTask = commentService.findPagedCommentsForTask(taskCode, pageable)
                .stream()
                .map(CommentController::buildCommentDTO)
                .toList();

        return new ResponseEntity<>(pagedCommentsForTask,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteCommentForTask(
            @PathVariable("commentId") Long commentId
    ){
        commentService.deleteCommentForTask(commentId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private static CommentDTO buildCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .text(comment.getText())
                .date(comment.getDate())
                .username(comment.getUser().getUsername())
                .build();
    }
}
