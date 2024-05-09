import {Component, OnInit} from '@angular/core';
import {Task} from "../../domain/task";
import {User} from "../../domain/user";
import {UserService} from "../../services/user.service";
import {TaskService} from "../../services/task.service";

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css'
})
export class TaskDetailsComponent implements OnInit{
  currentTask : Task;
  taskUsers: User[] = [];
  page:number = 0;

  constructor(private taskService: TaskService) {
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

  }
}
