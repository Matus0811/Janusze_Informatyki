import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Bug} from "../../domain/bug";
import {AddTaskFormComponent} from "../add-task-form/add-task-form.component";
import {TaskService} from "../../services/task.service";
import {response} from "express";
import {Project} from "../../domain/project";

@Component({
  selector: 'app-bug-details',
  templateUrl: './bug-details.component.html',
  styleUrl: './bug-details.component.css'
})
export class BugDetailsComponent {
  bugToShow!: Bug;
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<BugDetailsComponent>,
    private dialog: MatDialog,
    private taskService: TaskService
  ) {
    this.bugToShow = this.data.bugToShow;
  }

  closeDialog() {
    this.dialogRef.close();
  }

  createTask() {
    let matDialogRef = this.dialog.open(AddTaskFormComponent,{
      data: {
        createBugTask: true
      }
    });
    matDialogRef.afterClosed().subscribe(value => {
      if(value){
        console.log(value);
        let taskForm = this.taskService.createTaskForm(value,this.data.project.projectId);
        taskForm.status="BUG";
        this.taskService.createTask(taskForm).then(response => {
          console.log(`Task [${response}] for bug [${this.bugToShow.title}] created`);
          this.bugToShow.fixedTaskCreated = true;
        });
      }
    });
    this.dialogRef.close();
  }
}
