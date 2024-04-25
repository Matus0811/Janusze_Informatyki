import {Injectable} from '@angular/core';
import {AxiosService} from "./axios.service";
import {environment} from "../../environment/environment";
import {User} from "../domain/user";
import {jwtDecode} from "jwt-decode";
import {response} from "express";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userEndpoint = "/users";

  constructor(private axiosService: AxiosService) {
  }

  processUserLogin(userToLogin: any) {
    this.axiosService.request("POST", `${environment.url}/users/login`, userToLogin)
      .then(r => {
        console.log(r)
        this.axiosService.setAuthToken(r.data.token);
      })
      .catch(e => console.log(e));
  }

  getLoggedUser(): User | null {
    let token = this.axiosService.getAuthToken();

    if(token !== null){

    }
    return null;
  }

  processUserRegister(userToRegister: any){
    this.axiosService.request("POST",`${environment.url}/users/register`,userToRegister)
      .then(response => console.log(response));
  }
}
