package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.CommentRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.AppException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final TaskService taskService;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment processCommentCreation(CommentForm commentForm) {
        Task task = taskService.findByTaskCode(commentForm.getTaskCode());
        User user = userService.findByUsername(commentForm.getUsername());
        String commentText = commentForm.getText();

        if(commentText.trim().isEmpty()){
            throw new AppException("Comment empty!", HttpStatus.NOT_ACCEPTABLE);
        }
        Comment newComment = Comment.buildComment(commentText, task, user);

        return commentRepository.save(newComment);
    }

    public void removeUserCommentsInProject(String userEmail, UUID projectId){
        List<Comment> commentsToDelete = commentRepository.findAllUserCommentsInProject(userEmail,projectId);
        commentRepository.removeUserCommentsInProject(commentsToDelete);
    }

    @Transactional
    public List<Comment> findPagedCommentsForTask(String taskCode,Pageable pageable){
        Task task = taskService.findByTaskCode(taskCode);

        return commentRepository.findPagedCommentsForTask(task.getTaskId(),pageable);
    }

    public void deleteComment(Long commentToDeleteId) {
        commentRepository.deleteComment(commentToDeleteId);
    }
}
