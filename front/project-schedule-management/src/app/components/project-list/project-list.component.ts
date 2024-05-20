import {Component, Input, OnInit} from '@angular/core';
import {Project} from "../../domain/project";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {AddProjectFormComponent} from "../add-project-form/add-project-form.component";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit{
  projects: Project[] = [];
  page = 0;
  lastLoadedPageSize = 0;


  constructor(private projectService: ProjectService,
              private userService: UserService,
              private dialog: MatDialog,
  ) {
  }
  ngOnInit() {
    this.loadProjects();
  }

  removeProject(projectToRemove:Project) {
    this.projectService.removeProject(projectToRemove)
      .then(response => {
        this.projects = this.projects.filter(p => p.projectId !== projectToRemove.projectId);
      })
  }


  private loadProjects() {
    let currentUser = this.userService.getLoggedUserData();
    this.projectService.findPagedUserProjects(currentUser.email, this.page).then(
      value => {
        this.projects = value.data;
        this.lastLoadedPageSize = value.data.length;
      }
    ).catch(reason => console.log(reason));
  }

  openModal() {
    const dialogRef = this.dialog.open(AddProjectFormComponent);

    dialogRef.afterClosed().subscribe(result => {
        if(result){
          let projectForm = this.projectService.createProjectForm(result);
          console.log(projectForm);
          this.projectService.createProject(projectForm).then(res => {
              this.page = 0;
              this.loadProjects();
            }
          );
        }
      }
    );
  }

  public loadProjectPage() {
    let currentUser = this.userService.getLoggedUserData();
    this.page++;

    this.projectService.findPagedUserProjects(currentUser.email, this.page).then(
      value => {
        this.lastLoadedPageSize = value.data.length;
        this.projects = [...this.projects, ...value.data];
      }
    ).catch(reason => console.log(reason));
  }
}
