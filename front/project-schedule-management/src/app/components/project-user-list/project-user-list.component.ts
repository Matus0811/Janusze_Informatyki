import {Component, OnInit} from '@angular/core';
import {TaskStatus} from "../../domain/task-status";
import {User} from "../../domain/user";
import {ProjectService} from "../../services/project.service";
import {Router} from "@angular/router";
import {TaskService} from "../../services/task.service";
import {MatDialog} from "@angular/material/dialog";
import {AddUsersToTaskComponent} from "../add-users-to-task/add-users-to-task.component";
import {AddUserToProjectFormComponent} from "../add-user-to-project-form/add-user-to-project-form.component";
import {response} from "express";
import {Project} from "../../domain/project";

@Component({
  selector: 'app-project-user-list',
  templateUrl: './project-user-list.component.html',
  styleUrl: './project-user-list.component.css'
})
export class ProjectUserListComponent implements OnInit {
  projectUsers: User[] = [];
  page:number = 0;
  lastLoadedUsersSize: number = 0;
  projectId:string = this.router.url.split("/")[4];
  project!: Project;

  constructor(private projectService: ProjectService,
              private router: Router,
              private taskService: TaskService,
              private dialogRef: MatDialog) {

  }

  ngOnInit(): void {
    this.getPagedProjectMembers();
    this.getProject();
  }

  private getProject() {
    this.projectService.findProjectById(this.projectId).then(value => {
      this.project = value.data;
    })
  }

  getPagedProjectMembers(){
    this.page=0;
    this.projectService.getProjectMembers(this.projectId,this.page)
      .then(response => {
        this.projectUsers = response.data;
        this.lastLoadedUsersSize = response.data.length;
        this.page++;
      })
  }

  openModal() {
    const dialogRef = this.dialogRef.open(AddUserToProjectFormComponent);

    dialogRef.afterClosed().subscribe(usersToAdd => {
      if(usersToAdd){
        this.projectService.addUsersToProject(this.projectId,usersToAdd)
          .then(response => {
            this.projectUsers = [...this.projectUsers,...usersToAdd];
          });
      }
    })
  }

  removeUser(userToRemove: User) {
    this.projectService.removeUserFromProject(this.projectId, userToRemove)
      .then(response => {
        this.projectUsers = this.projectUsers.filter(u => u.username !== userToRemove.username);
      })
  }

  loadMoreUsers() {
    this.projectService.getProjectMembers(this.projectId,this.page)
      .then(response => {
        this.projectUsers = [...this.projectUsers,...response.data];
        this.lastLoadedUsersSize = response.data.length;
        this.page++;
      })
  }
}
