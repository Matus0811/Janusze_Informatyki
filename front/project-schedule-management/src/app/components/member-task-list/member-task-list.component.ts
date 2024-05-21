import {Component, Input} from '@angular/core';
import { Task } from '../../domain/task';
import {Priority} from "../../domain/priority";

@Component({
  selector: 'app-member-task-list',
  templateUrl: './member-task-list.component.html',
  styleUrl: './member-task-list.component.css'
})
export class MemberTaskListComponent {
  @Input() userTasks!: Task[];

}
