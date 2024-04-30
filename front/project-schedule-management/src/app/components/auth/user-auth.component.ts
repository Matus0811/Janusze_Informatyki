import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../domain/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-auth',
  templateUrl: './user-auth.component.html',
  styleUrl: './user-auth.component.css'
})
export class UserAuthComponent {
  loginPage = true;
  loggedUser!: User | null;
  constructor(private userService: UserService, private router: Router) {
  }
  handleLogin(userCredentials: any) {
    this.userService.processUserLogin(userCredentials);
  }

  handleRegister(userToRegister: any){
    console.log(userToRegister);
    this.userService.processUserRegister(userToRegister)
  }

  activeLoginPage() {
    this.loginPage=true;
  }

  activeRegisterPage() {
    this.loginPage=false;
  }
}
