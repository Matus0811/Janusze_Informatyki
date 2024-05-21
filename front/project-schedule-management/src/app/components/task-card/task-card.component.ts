import {Component, Input} from '@angular/core';
import { Task } from '../../domain/task';
import { Priority } from '../../domain/priority';

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrl: './task-card.component.css'
})
export class TaskCardComponent {
  @Input() task!: Task;

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
}
