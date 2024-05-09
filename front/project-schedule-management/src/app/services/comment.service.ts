import { Injectable } from '@angular/core';
import {CommentForm} from "../domain/comment-form";
import instance from "../http-axios";
import {Comment} from "../domain/comment";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor() {

  }

  addComment(commentToAdd: CommentForm) {
    return instance.request({
      method: "POST",
      url: "/comments/add",
      data: commentToAdd

    })
  }

  loadPagedComments(taskCode: string, page: number) {
    return instance.request({
      method: "GET",
      url: "/comments",
      params: {
        page : page,
        taskCode: taskCode
      }
    })
  }

  removeComment(comment: Comment) {
    return instance.request({
      method: "DELETE",
      url: "/comments/delete",
      data: comment
    })
  }
}
