import {Component, OnInit} from '@angular/core';
import {Task} from "../../domain/task";
import {User} from "../../domain/user";
import {TaskService} from "../../services/task.service";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {AddUsersToTaskComponent} from "../add-users-to-task/add-users-to-task.component";
import { Priority } from '../../domain/priority';
import {UserService} from "../../services/user.service";
import {UserTask} from "../../domain/user-task";

@Component({
  selector: 'app-member-task-details',
  templateUrl: './member-task-details.component.html',
  styleUrl: './member-task-details.component.css'
})
export class MemberTaskDetailsComponent implements OnInit{
  userTask : UserTask;
  task!: Task;
  projectId!: string;
  taskUsers: UserTask[] = [];
  page:number = 0;
  bugsListVisible = true;
  isFinishedTask!: boolean;

  constructor(
    private taskService: TaskService,
    private router: Router,
    private userService: UserService
  ) {
    this.userTask = this.router.getCurrentNavigation()?.extras.state?.['userTask'];
    this.projectId = this.router.getCurrentNavigation()?.extras.state?.['projectId'];

    if(this.userTask){
      this.task = this.userTask.task;
      this.isFinishedTask = this.userTask.finished;
    }
  }

  ngOnInit(): void {
    this.getUsersAssignedToTask();

  }

  getUsersAssignedToTask(){
    this.taskService.getPagedUsersAssignedToTask(this.task.taskCode,this.page)
      .then(response => {
        this.taskUsers = [...this.taskUsers,...response.data];
        this.page++;
      });
  }

  showTaskBugs() {
    this.bugsListVisible = !this.bugsListVisible;
  }

  public getColor(taskPriority: string): Priority {
    if (taskPriority === 'LOW') {
      return this.Priority.LOW;
    }
    if (taskPriority === 'MEDIUM') {
      return this.Priority.MEDIUM;
    }
    return this.Priority.HIGH;
  }

  protected readonly Priority = Priority;

  finishTask(currentTask: Task) {
    this.taskService.finishTask(currentTask, this.userService.getLoggedUserData().username, this.projectId)
      .then(response => {
        console.log(response);
        this.isFinishedTask = true;
      })
  }

  getFinishColor(finished: boolean) {
    if(finished){
      return this.Priority.LOW;
    }else {
      return this.Priority.HIGH;
    }
  }
}
