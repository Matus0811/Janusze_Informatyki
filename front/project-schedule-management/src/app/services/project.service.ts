import { Injectable } from '@angular/core';
import {TokenService} from "./token.service";
import {environment} from "../../environment/environment";
import axios from "axios";
import instance from "../http-axios";
import {Project} from "../domain/project";
import {ProjectForm} from "../domain/project-form";
import {UserService} from "./user.service";
import {UUID} from "node:crypto";
import httpAxios from "../http-axios";
import {User} from "../domain/user";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {


  constructor(private userService: UserService) { }

  findAllProjects(){
    // this.axiosService.request("GET",`${environment.url}/`)
  }

  findPagedUserProjects(projectsOwnerEmail: string | undefined, page: number) {
    return instance.request({
      method: "GET",
      url:`/projects`,
      params: {
        page: page,
        email: projectsOwnerEmail
      }
  });
  }

  findPagedMemberProjects(memberUsername: string | undefined, page: number){
    return instance.request({
      method: "GET",
      url: "/projects/member-project-list",
      params: {
        page: page,
        email: memberUsername
      }
    })
  }

  createProject(projectForm: any) {
    return instance.request({
      method: "POST",
      url: '/projects/create',
      data: projectForm
    })
  }

  createProjectForm(value: any ): ProjectForm {
    return {
      email: this.userService.getLoggedUserData().email,
      name: value.name,
      description: value.description,
      finishDate: new Date(value.finishDate)
    };
  }

  getProjectMembers(projectId:string, page: number) {
    return instance.request({
      method: 'GET',
      url: `/projects/${projectId}/project-members`,
      params: {
        page: page
      }
    })
  }

  countProjectMembers(projectId: UUID | undefined){
    return instance.request({
      method: "GET",
      url: `/projects/${projectId}/project-members-size`
    })
  }

  removeUserFromProject(projectId:UUID | undefined | string,userToRemove: User) {
    return instance.request({
      method: "DELETE",
      url: `/projects/${projectId}/remove-user`,
      params: {
        memberEmail: userToRemove.email
      }
    })
  }

  addUsersToProject(projectId: string, usersToAdd: any) {
    return instance.request({
      method: "POST",
      url: `/projects/${projectId}/add-users`,
      data: usersToAdd
    })
  }

  removeProject(projectToRemove: Project) {
    return instance.request({
      method: "DELETE",
      url: `/projects/delete/${projectToRemove.projectId}`
    })
  }

  generatePdf(project: Project) {
    return instance.request({
      method: "GET",
      responseType: "blob",
      headers: {
        'Accept' : 'application/pdf'
      },
      url: "/project/pdf-generate",
      params : {
        projectId: project.projectId
      }
    })
  }

  finishProject(project: Project,finishDate: Date) {
    return instance.request({
      method: "PUT",
      url: `/projects/${project.projectId}/finish`,
      params: {
        finishDate: new Date(finishDate)
      }
    })
  }

  findProjectById(projectId: string) {
    return instance.request({
      method: "GET",
      url: `/projects/${projectId}`
    })
  }
}
