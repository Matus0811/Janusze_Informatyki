import {Component} from '@angular/core';
import {Project} from "../../domain/project";
import {User} from "../../domain/user";
import {UserService} from "../../services/user.service";
import { Task } from '../../domain/task';
import {TaskService} from "../../services/task.service";

@Component({
  selector: 'app-project-member-view',
  templateUrl: './project-member-view.component.html',
  styleUrl: './project-member-view.component.css'
})
export class ProjectMemberViewComponent {
  project!: Project;
  user!: User;
  userTasks!: Task[];
  page: number = 0;

  constructor(private userService: UserService,private taskService:TaskService) {
    this.project = history.state.project;
    this.user = this.userService.getLoggedUserData();

    this.loadUserTasks();
  }

  private loadUserTasks() {
    this.taskService.getPagedMemberTasks(this.project, this.page, this.user)
      .then(response => {
        this.userTasks = response.data;
        if(response.data.length == 12){
          this.page++;
        }
      });
  }
}
