import {Injectable} from '@angular/core';
import {TokenService} from "./token.service";
import {environment} from "../../environment/environment";
import {User} from "../domain/user";
import {jwtDecode} from "jwt-decode";
import {response} from "express";
import {Router} from "@angular/router";
import instance from "../http-axios";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userEndpoint = "/users";
  isAuthenticated = false;

  constructor(private tokenService: TokenService, private router: Router) {
  }

  processUserLogin(userToLogin: any) {
    instance.request({
      method: "POST",
      url: `${this.userEndpoint}/login`,
      data: userToLogin
    })
      .then(r => {
        this.tokenService.setAuthToken(r.data.token);
        this.isAuthenticated = true;
        instance.defaults.headers.common['Authorization'] = `Bearer ${this.tokenService.getAuthToken()}`;

        this.router.navigate(["/projects"]);
      })
      .catch(e => console.log(e));
  }


  processUserRegister(userToRegister: any) {
    instance.request({
      method: "POST",
      url: `/users/register`,
      data: userToRegister
    }).then(response => console.log("USER REGISTERED SUCCESFULLY"))
      .catch(reason => console.log(reason));
  }

  logoutUser() {
    if (this.tokenService.removeAuthToken()) {
      this.router.navigate(['/auth/login'])
      delete instance.defaults.headers.common['Authorization'];
    }
  }

  getLoggedUserData(): User {
    let data = localStorage.getItem("logged_user");
    let user: User = {};
    if (data) {
      user = JSON.parse(data);
    }
    console.log(user);

    return user;
  }
}
