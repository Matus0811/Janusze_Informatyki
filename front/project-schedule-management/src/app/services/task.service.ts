import { Injectable } from '@angular/core';
import {UUID} from "node:crypto";
import httpAxios from "../http-axios";
import {TaskStatusCount} from "../domain/task-status-count";
import {AxiosResponse} from "axios";
import instance from "../http-axios";
import {TaskStatus} from "../domain/task-status";
import {TaskForm} from "../domain/task-form";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor() { }

  getGroupedProjectTaskByStatus(projectId: UUID | undefined)  {
    return instance.request({
      method: 'GET',
      url: '/tasks/grouped',
      params: {
        projectId: projectId
      }
    });
  }

  getPagedTasksForProject(projectId: string, page: number, taskStatus: TaskStatus) {
    return instance.request({
      method: 'GET',
      url: '/tasks',
      params: {
        projectId: projectId,
        page: page,
        taskStatus: taskStatus.toString()
      }
      });
  }

  removeTask(taskCode: string) {
    return instance.request({
      method: 'DELETE',
      url: `/tasks/${taskCode}/delete`
    });
  }

  addTask(taskForm: any) {
    console.log(taskForm);
  }

  createTask(taskForm: any) {
    return instance.request({
      method:"POST",
      url: "/tasks/create",
      data: taskForm
    });
  }

  createTaskForm(taskFormData: any, projectId:string): TaskForm {
    return {
      name: taskFormData.name,
      description: taskFormData.description,
      status: taskFormData.status,
      priority: taskFormData.priority,
      finishDate: new Date(taskFormData.finishDate),
      projectId: projectId
    };
  }

  getPagedUsersAssignedToTask(taskCode: string, page: number) {
    return instance.request({
      method: "GET",
      url: `/tasks/${taskCode}/users`,
      params: {
        page: page
      }
    });
  }
}
