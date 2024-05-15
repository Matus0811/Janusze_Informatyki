import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {User} from "../../domain/user";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-user-to-project-form',
  templateUrl: './add-user-to-project-form.component.html',
  styleUrl: './add-user-to-project-form.component.css'
})
export class AddUserToProjectFormComponent {
  formGroup = new FormGroup({
    username: new FormControl('')
  });
  usersToAdd: User[] = [];
  foundUsers: User[] = [];
  lastLoadUsersSize: number = 0;
  projectId: string = this.router.url.split("/")[4];
  page: number = 0;

  constructor(
    private userService: UserService,
    private router: Router,
    private dialogRef: MatDialogRef<AddUserToProjectFormComponent>
  ) {
    this.findAvailableUsers();
  }

  findAvailableUsers() {
    this.page = 0;

    this.userService.findAvailableUsers(this.projectId, this.page, this.formGroup.controls.username.value)
      .then(response => {
        this.foundUsers = response.data;
        this.foundUsers = this.foundUsers.filter(u => {
          return !this.usersToAdd.find(u2 => u2.username === u.username);
        });
        this.lastLoadUsersSize = response.data.length;
        this.page++;
      });
  }

  findMoreUsers() {
    this.userService.findAvailableUsers(this.projectId, this.page, this.formGroup.controls.username.value)
      .then(response => {
        this.foundUsers = [...this.foundUsers,...response.data];
        this.foundUsers = this.foundUsers.filter(u => {
          return !this.usersToAdd.find(u2 => u2.username === u.username);
        });
        this.lastLoadUsersSize = response.data.length;
        this.page++;
      })
  }

  addUser(user: User) {
    this.usersToAdd.push(user);
    this.foundUsers = this.foundUsers.filter(u => u.username !== user.username);

    if(this.foundUsers.length == 0){
      this.findMoreUsers();
    }
  }

  removeUser(user: User) {
    this.usersToAdd = this.usersToAdd.filter(u => u.username !== user.username);
    this.foundUsers.push(user);
  }

  close() {
    this.dialogRef.close();
  }

  submitUserAdd() {
    this.dialogRef.close(this.usersToAdd);
  }
}
