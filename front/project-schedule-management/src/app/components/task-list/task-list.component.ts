import {Component, OnInit, ViewChild} from '@angular/core';
import {Task} from "../../domain/task";
import {TaskService} from "../../services/task.service";
import {TaskStatus} from "../../domain/task-status";
import {ActivatedRoute, Router} from "@angular/router";
import {Priority} from "../../domain/priority";
import {MatDialog} from "@angular/material/dialog";
import {AddTaskFormComponent} from "../add-task-form/add-task-form.component";
import {ProjectService} from "../../services/project.service";
import {Project} from "../../domain/project";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  page: number = 0;
  lastLoadedTaskSize: number = 0;
  currentTaskStatus: TaskStatus = TaskStatus.ALL;
  projectId: string = this.router.url.split("/")[4];
  project!: Project;

  constructor(
    private taskService: TaskService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private dialog: MatDialog,
    private projectService: ProjectService
  ) {
  }

  ngOnInit(): void {
    this.loadTasks(this.currentTaskStatus);
    this.loadProject();
  }

  private loadProject() {
    this.projectService.findProjectById(this.projectId).then(response => {
      this.project = response.data;
    });
  }

  public loadMoreTasks() {
    this.loadTasks(this.currentTaskStatus);
  }

  public loadTasks(taskStatus: TaskStatus) {
    // if(taskStatus === this.currentTaskStatus){
    //   return;
    // }
    this.clearSavedTasks(taskStatus);

    this.taskService.getPagedTasksForProject(this.projectId, this.page, this.currentTaskStatus)
      .then(response => {
        this.lastLoadedTaskSize = response.data.length;
        this.tasks = [...this.tasks, ...response.data];
        this.page++;
      });
  }

  private clearSavedTasks(taskStatus: TaskStatus) {
    if (taskStatus != this.currentTaskStatus) {
      this.currentTaskStatus = taskStatus;
      this.tasks = [];
      this.page = 0;
    }
  }

  public getColor(taskPriority: string): Priority {
    if (taskPriority === 'LOW') {
      return this.Priority.LOW;
    }
    if (taskPriority === 'MEDIUM') {
      return this.Priority.MEDIUM;
    }
    return this.Priority.HIGH;
  }

  protected readonly TaskStatus = TaskStatus;
  protected readonly Priority = Priority;

  removeTask(taskCode: string) {
    this.taskService.removeTask(taskCode)
      .then(response => {
        this.tasks = this.tasks.filter(task => task.taskCode !== taskCode);
        console.log(response);
      });
  }

  showTaskDetails(task: Task) {
    this.router.navigate(["../", "task", "id"], {
      relativeTo: this.activatedRoute,
      queryParams: {id: task.taskCode},
      state: {
        task: task,
        projectId: this.projectId
      }
    })
      .catch(e => console.log(e));
  }

  openModal() {
    const dialogRef = this.dialog.open(AddTaskFormComponent);

    dialogRef.afterClosed().subscribe(result => {
        if (result) {
          let taskToCreate = this.taskService.createTaskForm(result, this.projectId);
          this.taskService.createTask(taskToCreate).then(response => {
              let createdTask: Task = response.data;
              this.tasks = [createdTask, ...this.tasks];
            }
          );
        }
      }
    );
  }
}
