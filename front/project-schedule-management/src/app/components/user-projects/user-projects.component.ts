import {Component, HostListener, OnInit} from '@angular/core';
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
export class UserProjectsComponent implements OnInit{
  projects: Project[] = [];
  page = 0;

  constructor(private projectService: ProjectService,
              private userService: UserService,
              private dialog: MatDialog,
              private router: Router
  ) {}

  ngOnInit() {
    this.loadProjects();
  }

  private loadProjects() {
    let currentUser = this.userService.getLoggedUserData();
    this.projectService.findPagedUserProjects(currentUser.email,this.page).then(
      value => {
        this.projects = value.data;
        console.log(this.page);
      }
    ).catch(reason => console.log(reason));
  }

  openModal() {
    const dialogRef = this.dialog.open(AddProjectFormComponent);

    dialogRef.afterClosed().subscribe(result =>{
      let projectForm = this.projectService.createProjectForm(result);
      console.log(`PROJECT FROM OPENMODAL METHOD: ${JSON.stringify(projectForm)}`);
      this.projectService.createProject(projectForm).then(
        res => {
          this.page = 0;
          this.loadProjects();
        }
      );
      this.router.navigate(["/projects/my-projects"]);
    }
  );
  }

  @HostListener('window:scroll',[])
  onWindowScroll(){
    const pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
    const max = document.documentElement.scrollHeight;
    console.log(pos);
    console.log(max);
    if (Math.ceil(pos) === max) {
      this.loadProjectPage();
    }
  }

  private loadProjectPage() {
    let currentUser = this.userService.getLoggedUserData();
    this.page++;

    this.projectService.findPagedUserProjects(currentUser.email,this.page).then(
      value => {
        this.projects = [...this.projects, ...value.data]
      }
    ).catch(reason => console.log(reason));
  }
}
