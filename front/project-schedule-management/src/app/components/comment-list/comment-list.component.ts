import {Component, Input, OnInit} from '@angular/core';
import {CommentService} from "../../services/comment.service";
import {Comment} from "../../domain/comment";
import {CommentForm} from "../../domain/comment-form";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrl: './comment-list.component.css'
})
export class CommentListComponent implements OnInit {
  comments: Comment[] = [];
  page: number = 0;
  @Input() taskCode: string = "";

  constructor(private commentService: CommentService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.loadPagedComments();
  }

  addComment(commentText: string) {
    let commentToAdd = this.buildCommentForm(commentText);

    this.commentService.addComment(commentToAdd)
      .then(response => {
        this.comments = [response.data, ...this.comments];
        this.page = 0;
      });
  }

  private buildCommentForm(commentText: string): CommentForm {
    return {
      text: commentText,
      taskCode: this.taskCode,
      username: this.userService.getLoggedUserData().username
    }
  }

  private loadPagedComments() {
    this.commentService.loadPagedComments(this.taskCode, this.page)
      .then(response => {
        this.comments = [...response.data, ...this.comments];
        this.page++;
      })
  }

  canDelete(comment: Comment) {
    return this.userService.getLoggedUsername() === comment.username;
  }

  removeComment(commentToDelete: Comment) {
    this.commentService.removeComment(commentToDelete)
      .then(response => {
          this.comments = this.comments.filter(deletedComment => deletedComment.commentId !== commentToDelete.commentId);
          console.log(response);
        }
      );
  }
}
