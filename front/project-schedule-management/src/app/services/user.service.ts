import {Injectable} from '@angular/core';
import {TokenService} from "./token.service";
import {User} from "../domain/user";
import {Router} from "@angular/router";
import instance from "../http-axios";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userEndpoint = "/users";

  constructor(private tokenService: TokenService, private router: Router) {
  }

  processUserLogin(userToLogin: any) {
    return instance.request({
      method: "POST",
      url: `${this.userEndpoint}/login`,
      data: userToLogin
    });
  }


  processUserRegister(userToRegister: any) {
    return instance.request({
      method: "POST",
      url: `/users/register`,
      data: userToRegister
    });
  }

  logoutUser() {
    if (this.tokenService.removeAuthToken()) {
      this.router.navigate(['/auth/login'])
      delete instance.defaults.headers.common['Authorization'];
    }
  }

  getLoggedUserData(): User {
    let data = localStorage.getItem("logged_user");
    let user;
    if (data) {
      user = JSON.parse(data);
    }
    return user;
  }

  public getLoggedUsername() {
    return this.getLoggedUserData().username;
  }


  findUsersInProjectNotAssignedToTask(projectId: string, taskCode: string, username: string | null, page: number) {
    return instance.request({
      method: "GET",
      url: `/projects/${projectId}/task/${taskCode}`,
      params: {
        username: username,
        page: page
      }
    })
  }

  findAvailableUsers(projectId: string, page: number, username: string | null) {
    return instance.request({
      method: "GET",
      url: `/projects/${projectId}/unassigned-users`,
      params: {
        username: username,
        page: page,
      }
    })
  }
}
