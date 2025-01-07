import {Component, Input} from '@angular/core';
import {Project} from "../../domain/project";
import {Router} from "@angular/router";

@Component({
  selector: 'app-project-member-card',
  templateUrl: './project-member-card.component.html',
  styleUrl: './project-member-card.component.css'
})
export class ProjectMemberCardComponent {
  @Input() project!: Project;

  constructor(private router: Router) {
  }

  goToProject() {
    this.router.navigate(["projects","projects-member","project", this.project.projectId],{
      state:{
        project: this.project
      }
    });
  }
}
