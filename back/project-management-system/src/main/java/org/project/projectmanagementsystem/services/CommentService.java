package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.CommentRepository;
import org.project.projectmanagementsystem.domain.Comment;
import org.project.projectmanagementsystem.domain.CommentForm;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final TaskService taskService;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment processCommentCreation(CommentForm commentForm) {
        Task task = taskService.findByTaskCode(commentForm.getTaskCode());
        User user = userService.findByEmail(commentForm.getUserEmail());
        String commentText = commentForm.getText();

        Comment newComment = Comment.buildComment(commentText, task, user);

        return commentRepository.save(newComment);
    }

    @Transactional
    public List<Comment> findPagedCommentsForTask(String taskCode,Pageable pageable){
        Task task = taskService.findByTaskCode(taskCode);

        return commentRepository.findPagedCommentsForTask(task.getTaskId(),pageable);
    }

    public void deleteCommentForTask(Long commentId) {
        commentRepository.deleteCommentWhereId(commentId);
    }
}
