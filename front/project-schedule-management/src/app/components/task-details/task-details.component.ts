import {Component, OnInit} from '@angular/core';
import {Task} from "../../domain/task";
import {User} from "../../domain/user";
import {UserService} from "../../services/user.service";
import {TaskService} from "../../services/task.service";
import {AddTaskFormComponent} from "../add-task-form/add-task-form.component";
import {AddUsersToTaskComponent} from "../add-users-to-task/add-users-to-task.component";
import {DialogRef} from "@angular/cdk/dialog";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css'
})
export class TaskDetailsComponent implements OnInit{
  currentTask : Task;
  taskUsers: User[] = [];
  page:number = 0;

  constructor(private taskService: TaskService, private dialog: MatDialog) {
    this.currentTask = this.currentTask = history.state.task;
    console.log(this.currentTask);
  }

  ngOnInit(): void {
    this.getUsersAssignedToTask();
  }

  getUsersAssignedToTask(){
    this.taskService.getPagedUsersAssignedToTask(this.currentTask.taskCode,this.page)
      .then(response => {
        this.taskUsers = [...this.taskUsers,...response.data];
        this.page++;
      });
  }

  addUsers(task: Task) {
    const dialogRef = this.dialog.open(AddUsersToTaskComponent,{
      data: task.taskCode
    });

    dialogRef.afterClosed().subscribe(result => {
      this.taskService.addUsersToTask(result,this.currentTask.taskCode)
        .then(response => {
          this.taskUsers = [...this.taskUsers, ...result];
          this.taskUsers.sort((u1,u2) => u1.username.localeCompare(u2.username));
          console.log(this.taskUsers);
        });
    })
  }
}
