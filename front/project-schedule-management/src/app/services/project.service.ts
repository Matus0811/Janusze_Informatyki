import { Injectable } from '@angular/core';
import {TokenService} from "./token.service";
import {environment} from "../../environment/environment";
import axios from "axios";
import instance from "../http-axios";
import {Project} from "../domain/project";
import {ProjectForm} from "../domain/project-form";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {


  constructor(private userService: UserService) { }

  findAllProjects(){
    // this.axiosService.request("GET",`${environment.url}/`)
  }

  findPagedUserProjects(email: string | undefined, page: number) {
    return instance.request({
      method: "GET",
      url:`/projects`,
      params: {
        page: page,
        email: email
      }
  });
  }

  createProject(projectForm: ProjectForm) {
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
}
