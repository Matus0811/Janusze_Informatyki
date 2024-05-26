import {Component, OnInit} from '@angular/core';
import {Bug} from "../../domain/bug";
import {BugService} from "../../services/bug.service";
import {Router} from "@angular/router";
import {Project} from "../../domain/project";
import {BugDetailsComponent} from "../bug-details/bug-details.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-project-bugs',
  templateUrl: './project-bugs.component.html',
  styleUrl: './project-bugs.component.css'
})
export class ProjectBugsComponent implements OnInit {
  projectBugs: Bug[] = [];
  project!: Project;
  lastLoadedPageSize: number = 0;
  page: number = 0;

  constructor(private bugService: BugService, private router: Router, private dialog: MatDialog) {
    this.project = this.router.getCurrentNavigation()?.extras.state?.['project'];
  }

  ngOnInit(): void {
    this.loadPagedBugs();
  }

  private loadPagedBugs() {
    this.bugService.getPagedBugListForProject(this.project, this.page)
      .then(response => {
        console.log(response.data);
        this.projectBugs = [...this.projectBugs, ...response.data];
        this.lastLoadedPageSize = response.data.length;

        if (this.lastLoadedPageSize == 8) {
          this.page++;
        }
      });
  }

  showBugDetails(bug: Bug) {
    this.dialog.open(BugDetailsComponent,{
      data: bug
    })
  }
}
