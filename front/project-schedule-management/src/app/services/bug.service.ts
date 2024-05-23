import { Injectable } from '@angular/core';
import {Project} from "../domain/project";
import instance from "../http-axios";
import {BugForm} from "../domain/bug-form";

@Injectable({
  providedIn: 'root'
})
export class BugService {

  constructor() { }

  countProjectBugs(project: Project) {
    return instance.request({
      method: "GET",
      url: "/bugs/size",
      params: {
        projectId: project.projectId
      }
    })
  }

  getPagedBugListForTask(taskCode: string, page: number) {
    console.log("TASK: " + taskCode);
    return instance.request({
      method: "GET",
      url: "/bugs/task-bug-list",
      params: {
        taskCode: taskCode,
        page: page
      }
    })
  }

  reportBug(bugForm: BugForm) {
    return instance.request({
      method: "POST",
      url: "bugs/report",
      data: bugForm
    })
  }
}
