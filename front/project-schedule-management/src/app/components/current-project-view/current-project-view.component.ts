import {Component, OnInit} from '@angular/core';
import {Project} from "../../domain/project";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-current-project-view',
  templateUrl: './current-project-view.component.html',
  styleUrl: './current-project-view.component.css'
})
export class CurrentProjectViewComponent implements OnInit {
  project: Project = {};

  ngOnInit(): void {
    this.project = history.state.project;
    console.log(this.project);
  }

}
