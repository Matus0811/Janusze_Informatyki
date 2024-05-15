import {Component, OnInit} from '@angular/core';
import {Project} from "../../domain/project";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {MatDialog} from "@angular/material/dialog";
import {AddProjectFormComponent} from "../add-project-form/add-project-form.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-projects',
  templateUrl: './user-projects.component.html',
  styleUrl: './user-projects.component.css'
})
export class UserProjectsComponent implements OnInit {
  projects: Project[] = [];
  page = 0;
  lastLoadedPageSize = 0;

  constructor(private projectService: ProjectService,
              private userService: UserService,
              private dialog: MatDialog,
              private router: Router
  ) {
  }

  ngOnInit() {
    this.loadProjects();
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
        let projectForm = this.projectService.createProjectForm(result);
        console.log(projectForm);
        this.projectService.createProject(projectForm).then(res => {
            this.page = 0;
            this.loadProjects();
          }
        );
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
