import { Component } from '@angular/core';
import {Project} from "../../domain/project";
import {ProjectService} from "../../services/project.service";

@Component({
  selector: 'app-project-content',
  templateUrl: './project-content.component.html',
  styleUrl: './project-content.component.css'
})
export class ProjectContentComponent {
  projects: Project[] = [];

  constructor(private projectService: ProjectService) {

  }
}
