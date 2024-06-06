import {Component, Input, OnInit} from '@angular/core';
import {Bug} from "../../domain/bug";
import {BugService} from "../../services/bug.service";
import {Project} from "../../domain/project";
import {MatDialog} from "@angular/material/dialog";
import {ReportBugFormComponent} from "../report-bug-form/report-bug-form.component";
import {BugForm} from "../../domain/bug-form";

import {UserService} from "../../services/user.service";
import {Task} from '../../domain/task';
import {BugDetailsComponent} from "../bug-details/bug-details.component";

@Component({
  selector: 'app-task-bugs',
  templateUrl: './task-bugs.component.html',
  styleUrl: './task-bugs.component.css'
})
export class TaskBugsComponent implements OnInit {
  @Input() task!: Task;
  @Input() projectId!: string;
  reportedBugs: Bug[] = [];
  page: number = 0;
  lastLoadedPageSize: number = 0;

  constructor(private bugService: BugService, private dialog: MatDialog, private userService: UserService) {
    console.log(`TaskBugsComponent: ${this.projectId}`);
  }

  ngOnInit(): void {
    this.findPagedBugsForTask();
  }

  public findPagedBugsForTask() {
    this.bugService.getPagedBugListForTask(this.task.taskCode, this.page)
      .then(response => {
        this.reportedBugs = [...this.reportedBugs, ...response.data];
        this.lastLoadedPageSize = response.data.length;

        if (response.data == 8) {
          this.page++;
        }
      })
  }

  showBugReportForm() {
    let dialogRef = this.dialog.open(ReportBugFormComponent);

    dialogRef.afterClosed().subscribe(value => {
      if (value) {
        let bugForm = this.buildBugForm(value);

        this.bugService.reportBug(bugForm)
          .then(response => {
            this.page = 0;
            this.findPagedBugsForTask();
          });
      }
    });
  }

  private buildBugForm(value: any): BugForm {
    return {
      title: value.title,
      description: value.description,
      bugType: value.bugType,
      projectId: this.projectId,
      taskCode: this.task.taskCode,
      username: this.userService.getLoggedUsername(),
      reportDate: new Date()
    }
  }

  showDetails(bug: Bug) {
    this.dialog.open(BugDetailsComponent, {
      data: {
        bugToShow: bug,
        projectId: this.projectId
      }
    });
  }
}
