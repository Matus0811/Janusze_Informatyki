import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CommentForm} from "../../domain/comment-form";

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrl: './add-comment.component.css'
})
export class AddCommentComponent {
  commentForm: FormGroup = new FormGroup({
    text: new FormControl('')
  });

  @Output() emitter = new EventEmitter<string>();
  addComment() {
    if(this.commentForm.valid){
      this.emitter.emit(this.commentForm.controls['text'].value);
    }
  }
}
