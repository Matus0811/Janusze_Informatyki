import {Component, Input, OnInit} from '@angular/core';
import {Task} from '../../domain/task';
import {Priority} from '../../domain/priority';
import {ActivatedRoute, Router} from "@angular/router";
import {UserTask} from "../../domain/user-task";

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrl: './task-card.component.css'
})
export class TaskCardComponent implements OnInit{
  @Input() loadedTask!: UserTask;
  task!: Task;
  projectId:string = this.router.url.split("/")[4];

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,) {
  }

  ngOnInit(): void {
    this.task = this.loadedTask.task;
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

  protected readonly Priority = Priority;

  goToTask(task: UserTask) {
    this.router.navigate(["task", "id"], {
      relativeTo: this.activatedRoute,
      queryParams: {id: this.task.taskCode},
      state: {
        userTask: task,
        projectId: this.projectId
      }
    })
      .catch(e => console.log(e));
  }
}
