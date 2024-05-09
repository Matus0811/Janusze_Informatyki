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
  projectMembers: User[] = [];
  page = 0;

  constructor(private taskService : TaskService, private projectService : ProjectService){}
  ngOnInit(): void {
    this.project = history.state.project;
    this.loadProjectMembers()
    this.loadGroupedTasks();
    this.projectMembersSize();
  }


  public loadGroupedTasks() {
    this.taskService.getGroupedProjectTaskByStatus(this.project.projectId)
      .then(response => {
        this.createChart(response.data);
        this.groupedTaskByStatusList = response.data;
      })
      .catch(error => console.log(error))
  }

  private loadProjectMembers() {
    this.projectService.getProjectMembers(this.project.projectId, this.page)
      .then(response => {
        this.projectMembers = response.data;
        this.page++;
      });
  }

  projectMembersSize(){
    return this.projectMembers.length;
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
