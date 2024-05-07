import {TaskStatus} from "./task-status";

export interface Task {
  taskCode: string;
  name: string;
  description: string;
  status: TaskStatus;
  priority: string;
  startDate: number;
  finishDate: number;
}
