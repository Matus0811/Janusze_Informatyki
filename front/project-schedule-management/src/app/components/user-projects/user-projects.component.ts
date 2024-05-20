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
export class UserProjectsComponent {
}
