import { Injectable } from '@angular/core';
import {AxiosService} from "./axios.service";
import {environment} from "../../environment/environment";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private axiosService: AxiosService) { }

  findAllProjects(){
    // this.axiosService.request("GET",`${environment.url}/`)
  }
}
