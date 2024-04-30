import { Component } from '@angular/core';
import {Project} from "../../domain/project";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-project-content',
  templateUrl: './project-content.component.html',
  styleUrl: './project-content.component.css'
})
export class ProjectContentComponent {


  constructor(private projectService: ProjectService, private userService : UserService, private router: Router) {

  }



}
