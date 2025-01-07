import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../domain/user";
import {Router} from "@angular/router";
import instance from "../../http-axios";
import {TokenService} from "../../services/token.service";
import {MessageHandlerService} from "../../services/message-handler.service";

@Component({
  selector: 'app-user-auth',
  templateUrl: './user-auth.component.html',
  styleUrl: './user-auth.component.css'
})
export class UserAuthComponent {
  loginPage = true;
  isAuthenticated = false;
  constructor(
    private userService: UserService,
    private router: Router,
    private tokenService: TokenService,
    private messageHandlerService: MessageHandlerService,
  ) {
  }
  handleLogin(userCredentials: any) {
    this.userService.processUserLogin(userCredentials)
      .then(r => {
        this.tokenService.setAuthToken(r.data.token);
        this.isAuthenticated = true;
        instance.defaults.headers.common['Authorization'] = `Bearer ${this.tokenService.getAuthToken()}`;

        this.router.navigate(["/projects"]);
      }).catch(e => this.messageHandlerService.handleException(e.response));
  }

  handleRegister(userToRegister: any){
    this.userService.processUserRegister(userToRegister)
      .then(response => {
        this.messageHandlerService.handleMessageInfo("Udało się zarejestrować użytkownika");
      })
      .catch(reason => {
        this.messageHandlerService.handleException(reason.response);
      })
  }

  activeLoginPage() {
    this.loginPage=true;
  }

  activeRegisterPage() {
    this.loginPage=false;
  }

}
