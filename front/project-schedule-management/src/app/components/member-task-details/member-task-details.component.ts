import {Component, OnInit} from '@angular/core';
import {Task} from "../../domain/task";
import {User} from "../../domain/user";
import {TaskService} from "../../services/task.service";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {AddUsersToTaskComponent} from "../add-users-to-task/add-users-to-task.component";
import { Priority } from '../../domain/priority';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-member-task-details',
  templateUrl: './member-task-details.component.html',
  styleUrl: './member-task-details.component.css'
})
export class MemberTaskDetailsComponent implements OnInit{
  currentTask : Task;
  projectId!: string;
  taskUsers: User[] = [];
  page:number = 0;
  bugsListVisible = true;
  isFinishedTask!: boolean;

  constructor(
    private taskService: TaskService,
    private router: Router,
    private userService: UserService
  ) {
    this.currentTask = this.router.getCurrentNavigation()?.extras.state?.['task'];
    this.projectId = this.router.getCurrentNavigation()?.extras.state?.['projectId'];
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
    this.taskService.finishTask(currentTask, this.userService.getLoggedUserData().email, this.projectId)
      .then(response => {
        console.log(response);

      })
  }
}
