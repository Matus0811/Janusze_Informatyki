import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../domain/user";
import {Router} from "@angular/router";
import instance from "../../http-axios";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-user-auth',
  templateUrl: './user-auth.component.html',
  styleUrl: './user-auth.component.css'
})
export class UserAuthComponent {
  loginPage = true;
  isAuthenticated = false;
  constructor(private userService: UserService, private router: Router,private tokenService: TokenService) {
  }
  handleLogin(userCredentials: any) {
    this.userService.processUserLogin(userCredentials)
      .then(r => {
        this.tokenService.setAuthToken(r.data.token);
        this.isAuthenticated = true;
        instance.defaults.headers.common['Authorization'] = `Bearer ${this.tokenService.getAuthToken()}`;

        this.router.navigate(["/projects"]);
      }).catch(e => console.log(e.response.data));
  }

  handleRegister(userToRegister: any){
    console.log(userToRegister);
    this.userService.processUserRegister(userToRegister)
      .catch(reason => {
        this.openErrorModalMessage(reason);
      })
  }

  activeLoginPage() {
    this.loginPage=true;
  }

  activeRegisterPage() {
    this.loginPage=false;
  }

  public openErrorModalMessage(reason: any) {

  }
}
