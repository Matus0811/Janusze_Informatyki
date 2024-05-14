import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../domain/user";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  loggedUser: User;
  constructor(private userService: UserService) {
    this.loggedUser = userService.getLoggedUserData();
  }
  logout() {
    this.userService.logoutUser();
  }
}
