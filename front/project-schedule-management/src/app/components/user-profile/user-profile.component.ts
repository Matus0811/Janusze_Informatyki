import {Component, OnInit} from '@angular/core';
import {User} from "../../domain/user";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit{
  loggedUser!: User;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getUserProfile(this.userService.getLoggedUserData().username)
      .then(response => {
        this.loggedUser = response.data;
      });
    }
}
