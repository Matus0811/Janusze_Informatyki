import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {TaskService} from "../../services/task.service";

@Component({
  selector: 'app-add-task-form',
  templateUrl: './add-task-form.component.html',
  styleUrl: './add-task-form.component.css'
})
export class AddTaskFormComponent {
  taskForm = new FormGroup({
    name: new FormControl('',[Validators.required]),
    description: new FormControl('',[Validators.required]),
    status: new FormControl('',Validators.required),
    priority: new FormControl('',Validators.required),
    finishDate: new FormControl(''),
  });

  constructor(private dialogRef: MatDialogRef<AddTaskFormComponent>, private taskService: TaskService) {
  }

  close() {
    this.dialogRef.close();
  }

  submitForm() {
    if(this.taskForm.valid){
      this.dialogRef.close(this.taskForm.value);
    }
  }
}
