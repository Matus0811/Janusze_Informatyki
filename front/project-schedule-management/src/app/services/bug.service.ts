import {Injectable} from '@angular/core';
import {Project} from "../domain/project";
import instance from "../http-axios";
import {BugForm} from "../domain/bug-form";
import {Bug} from "../domain/bug";

@Injectable({
  providedIn: 'root'
})
export class BugService {

  constructor() {
  }

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

  getPagedBugListForProject(project: Project, page: number) {
    console.log(project.projectId);
    return instance.request({
      method: "GET",
      url: "/bugs/list",
      params: {
        projectId: project.projectId,
        page: page
      }
    })
  }

  updateBugStatus(status: string, bug: Bug) {
    return instance.request({
      method: "PATCH",
      url: "/bugs/update-status",
      params: {
        newStatus: status,
        bugSerialNumber: bug.serialNumber
      }
    })
  }

  assignCreatedBugTaskToBug(createdTaskBug: any, bugToShow: Bug) {
    return instance.request({
      method:"PATCH",
      url: "/bugs/add-bug-task",
      params: {
        taskCode: createdTaskBug.taskCode,
        bugSerialNumber: bugToShow.serialNumber
      }
    })
  }
}
