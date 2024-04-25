import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../domain/user";

@Component({
  selector: 'app-user-auth',
  templateUrl: './user-auth.component.html',
  styleUrl: './user-auth.component.css'
})
export class UserAuthComponent {
  loginPage = true;
  loggedUser!: User | null;
  constructor(private userService: UserService) {
  }
  handleLogin(userCredentials: any) {
    this.userService.processUserLogin(userCredentials);

    //TODO dodać ifa który sprawdza czy w local storage istnieje token, jezeli tak to przekierowanie na strone glowna
    this.loggedUser = this.userService.getLoggedUser();
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
