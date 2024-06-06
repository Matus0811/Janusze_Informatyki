import {Component, OnInit} from '@angular/core';
import {Project} from "../../domain/project";
import {TaskService} from "../../services/task.service";
import {TaskStatusCount} from "../../domain/task-status-count";
import {User} from "../../domain/user";
import {ProjectService} from "../../services/project.service";
import {Chart} from "chart.js/auto";
import {BugService} from "../../services/bug.service";
import {response} from "express";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DateDetailsComponent} from "../date-details/date-details.component";
import {GlobalErrorHandlerComponent} from "../global-error-handler/global-error-handler.component";
import {MessageHandlerService} from "../../services/message-handler.service";

@Component({
  selector: 'app-current-project-view',
  templateUrl: './current-project-view.component.html',
  styleUrl: './current-project-view.component.css'
})
export class CurrentProjectViewComponent implements OnInit {
  project: Project = {};
  groupedTaskByStatusList: TaskStatusCount[] = [];
  projectMembersSize: number = 0;
  totalTasks: number = 0;
  numberOfBugsToFix: number = 0;

  constructor(
    private taskService: TaskService,
    private projectService: ProjectService,
    private bugService: BugService,
    private router: Router,
    private dialog: MatDialog,
    private errorHandlerService: MessageHandlerService
  ) {
  }

  ngOnInit(): void {
    this.project = history.state.project;
    this.loadGroupedTasks();
    this.countProjectMembers();
    this.countBugsToFix();
  }


  public loadGroupedTasks() {
    this.taskService.getGroupedProjectTaskByStatus(this.project.projectId)
      .then(response => {
        this.createChart(response.data);
        this.groupedTaskByStatusList = response.data;
        this.totalTasks = this.groupedTaskByStatusList.map(value => value.count)
          .reduce((prev, current) => prev + current);
      })
      .catch(error => console.log(error))
  }

  private countProjectMembers() {
    this.projectService.countProjectMembers(this.project.projectId)
      .then(response => {
        this.projectMembersSize = response.data;
      });
  }


  private createChart(groupedTaskByStatusList: TaskStatusCount[]) {
    const ctx = document.getElementById('chart') as HTMLCanvasElement;
    new Chart(ctx, {
      type: "doughnut",
      data: {
        labels: groupedTaskByStatusList.map(value => value.status),
        datasets: [{
          label: 'Liczba zadań',
          data: groupedTaskByStatusList.map(value => value.count)
        }]
      },
    });
  }

  private countBugsToFix() {
    this.bugService.countProjectBugs(this.project)
      .then(response => {
        this.numberOfBugsToFix = response.data;
      })
  }

  showBugs() {
    this.router.navigate(["projects", "my-projects", "project", this.project.projectId, "project-bugs"], {
      state: {
        project: this.project
      }
    }).catch(reason => console.log(reason));
  }

  showDate(finishDate: Date | undefined) {
    this.dialog.open(DateDetailsComponent, {
      data: {
        finishDate: finishDate
      }
    })
  }

  generatePdf() {
    let finishDate = new Date();
    this.projectService.finishProject(this.project,finishDate).then(response => {
      console.log(response);
      this.project.finishDate = finishDate;

      this.projectService.generatePdf(this.project).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'file.pdf');
        document.body.appendChild(link);
        link.click();
      })
    }).catch(reason => {
      console.log(reason);
      this.errorHandlerService.handleException(reason.response);
    });

  }
}
