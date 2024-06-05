import {Component, Input, OnInit} from '@angular/core';
import {Task} from '../../domain/task';
import {Priority} from '../../domain/priority';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrl: './task-card.component.css'
})
export class TaskCardComponent implements OnInit {
  @Input() loadedTask!: Task;
  task!: Task;
  projectId:string = this.router.url.split("/")[4];

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,) {
  }

  ngOnInit(): void {
    console.log(this.loadedTask);
    this.task = this.loadedTask;
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

  goToTask(task: Task) {
    this.router.navigate(["task", "id"], {
      relativeTo: this.activatedRoute,
      queryParams: {id: task.taskCode},
      state: {
        task: task,
        projectId: this.projectId
      }
    })
      .catch(e => console.log(e));
  }
}
