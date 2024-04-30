import {Component, Input} from '@angular/core';
import {Project} from "../../domain/project";
import {Router} from "@angular/router";

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrl: './project-card.component.css'
})
export class ProjectCardComponent {
  @Input() project!: Project;

  constructor(private router: Router) {
  }

  goToProject() {
    this.router.navigate(["projects","my-projects","project", this.project.projectId],{
      state:{
        project: this.project
      }
    });
  }
}
