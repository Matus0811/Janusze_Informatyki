import {Component, OnInit} from '@angular/core';
import {Project} from "../../domain/project";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {MatDialog} from "@angular/material/dialog";
import {AddProjectFormComponent} from "../add-project-form/add-project-form.component";

@Component({
  selector: 'app-project-member-list',
  templateUrl: './project-member-list.component.html',
  styleUrl: './project-member-list.component.css'
})
export class ProjectMemberListComponent implements OnInit{
  projects: Project[] = [];
  page = 0;
  lastLoadedPageSize = 0;

  constructor(private projectService: ProjectService,
              private userService: UserService,
  ) {
  }
  ngOnInit() {
    this.loadProjects();
  }

  private loadProjects() {
    let currentUser = this.userService.getLoggedUserData();
    this.projectService.findPagedMemberProjects(currentUser.email, this.page).then(
      value => {
        this.projects = value.data;
        this.lastLoadedPageSize = value.data.length;
      }
    ).catch(reason => console.log(reason));
  }

  public loadProjectPage() {
    let currentUser = this.userService.getLoggedUserData();
    this.page++;

    this.projectService.findPagedMemberProjects(currentUser.email, this.page).then(
      value => {
        this.lastLoadedPageSize = value.data.length;
        this.projects = [...this.projects, ...value.data];
      }
    ).catch(reason => console.log(reason));
  }
}
