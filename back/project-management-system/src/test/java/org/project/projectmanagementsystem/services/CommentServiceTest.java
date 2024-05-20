package org.project.projectmanagementsystem.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.projectmanagementsystem.database.CommentRepository;
import org.project.projectmanagementsystem.domain.Comment;
import org.project.projectmanagementsystem.domain.CommentForm;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.services.exceptions.AppException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private UserService userService;
    @Mock
    private TaskService taskService;
    @Mock
    private CommentRepository commentRepository;

    @Test
    void processCommentCreation() {
        //given
        String taskCode = UUID.randomUUID().toString();
        String username = "username1";
        String text = "This is some text";

        CommentForm commentForm = CommentForm.builder()
                .username(username)
                .text(text)
                .taskCode(taskCode)
                .build();
        User foundUser = User.builder()
                .userId(1L)
                .username(username)
                .build();

        Task foundTask = Task.builder()
                .taskId(1L)
                .taskCode(taskCode)
                .build();

        Comment expectedComment = Comment.builder()
                .commentId(1L)
                .text(text)
                .date(OffsetDateTime.now())
                .user(foundUser)
                .task(foundTask)
                .build();

        //when
        Mockito.when(userService.findByUsername(username)).thenReturn(foundUser);
        Mockito.when(taskService.findByTaskCode(taskCode)).thenReturn(foundTask);
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(expectedComment);

        Comment result = commentService.processCommentCreation(commentForm);

        //then
        Assertions.assertEquals(expectedComment,result);
    }

    @Test
    void should_throw_exception_while_passing_empty_text(){
        //given
        String taskCode = UUID.randomUUID().toString();
        String username = "username1";
        String text = "";

        CommentForm commentForm = CommentForm.builder()
                .username(username)
                .text(text)
                .taskCode(taskCode)
                .build();
        User foundUser = User.builder()
                .userId(1L)
                .username(username)
                .build();

        Task foundTask = Task.builder()
                .taskId(1L)
                .taskCode(taskCode)
                .build();

        Comment expectedComment = Comment.builder()
                .commentId(1L)
                .text(text)
                .date(OffsetDateTime.now())
                .user(foundUser)
                .task(foundTask)
                .build();

        //when
        Mockito.when(userService.findByUsername(username)).thenReturn(foundUser);
        Mockito.when(taskService.findByTaskCode(taskCode)).thenReturn(foundTask);

        AppException result = assertThrows(AppException.class, () -> commentService.processCommentCreation(commentForm));

        //then
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE,result.getStatus());
    }

    @Test
    void findPagedCommentsForTask() {
        //given
        Pageable pageable = PageRequest.of(1,6).withSort(Sort.by("date"));
        String taskCode = UUID.randomUUID().toString();
        Task task = Task.builder()
                .taskId(1L)
                .taskCode(taskCode)
                .build();
        User user1 = User.builder()
                .userId(1L)
                .username("user1")
                .build();
        User user2 = User.builder()
                .userId(2L)
                .username("user2")
                .build();

        List<Comment> expectedComments = List.of(
                Comment.buildComment("someText1",task,user1),
                Comment.buildComment("someText2",task,user2),
                Comment.buildComment("someText3",task,user1)
        );

        Mockito.when(commentRepository.findPagedCommentsForTask(1L,pageable)).thenReturn(expectedComments);
        Mockito.when(taskService.findByTaskCode(Mockito.anyString())).thenReturn(task);
        List<Comment> resultComments = commentService.findPagedCommentsForTask(taskCode, pageable);

        Assertions.assertEquals(expectedComments,resultComments);
        Assertions.assertEquals(expectedComments.size(),resultComments.size());
    }

    @Test
    void deleteComment() {
    }
}