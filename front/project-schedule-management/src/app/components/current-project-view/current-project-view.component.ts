import {Component, OnInit} from '@angular/core';
import {Project} from "../../domain/project";
import {TaskService} from "../../services/task.service";
import {TaskStatusCount} from "../../domain/task-status-count";
import {User} from "../../domain/user";
import {ProjectService} from "../../services/project.service";
import {Chart} from "chart.js/auto";

@Component({
  selector: 'app-current-project-view',
  templateUrl: './current-project-view.component.html',
  styleUrl: './current-project-view.component.css'
})
export class CurrentProjectViewComponent implements OnInit {
  project: Project = {};
  groupedTaskByStatusList: TaskStatusCount[] = [];
  projectMembersSize : number = 0;
  totalTasks: number = 0;

  constructor(private taskService : TaskService, private projectService : ProjectService){}
  ngOnInit(): void {
    this.project = history.state.project;
    this.loadGroupedTasks();
    this.countProjectMembers();
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
    new Chart(ctx,{
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
}
