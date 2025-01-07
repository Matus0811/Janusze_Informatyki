import { Injectable } from '@angular/core';
import {UUID} from "node:crypto";
import httpAxios from "../http-axios";
import {TaskStatusCount} from "../domain/task-status-count";
import {AxiosResponse} from "axios";
import instance from "../http-axios";
import {TaskStatus} from "../domain/task-status";
import {TaskForm} from "../domain/task-form";
import {User} from "../domain/user";
import {Project} from "../domain/project";
import { Task } from '../domain/task';

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

  createTaskForm(taskFormData: any, projectId:any): TaskForm {
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

  addUsersToTask(usersToAdd: User[], taskCode: string) {
    return instance.request({
      method: "PATCH",
      url: `/tasks/${taskCode}/add-users`,
      data : usersToAdd
    })
  }

  removeUserFromTask(taskCode: string, userToRemove: User) {
    return instance.request({
      method: "DELETE",
      url: `/${taskCode}/remove-user`
    })
  }

  getPagedMemberTasks(project: Project, page: number, user: User) {
    return instance.request({
      method: "GET",
      url: `/tasks/member-tasks`,
      params: {
        projectId: project.projectId,
        page: page,
        username: user.username
      }
    });
  }

  finishTask(currentTask: Task, username: string | undefined, projectId: string) {
    return instance.request({
      method: "PUT",
      url: `/tasks/${currentTask.taskCode}/users/finish-task`,
      params: {
        username: username,
        projectId: projectId
      }
    })
  }
}
