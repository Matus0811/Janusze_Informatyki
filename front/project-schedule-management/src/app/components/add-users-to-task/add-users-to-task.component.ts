import {Component, Inject, Input} from '@angular/core';
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup} from "@angular/forms";
import {User} from "../../domain/user";

@Component({
  selector: 'app-add-users-to-task',
  templateUrl: './add-users-to-task.component.html',
  styleUrl: './add-users-to-task.component.css'
})
export class AddUsersToTaskComponent {
  formGroup = new FormGroup({
    username: new FormControl('')
  });
  usersToAdd: User[] = [];
  foundUsers: User[] = [];
  projectId: string = this.router.url.split("/")[4];
  page: number = 0;

  constructor(
    private userService: UserService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) private taskCode: string,
    private dialogRef: MatDialogRef<AddUsersToTaskComponent>
  ) {

    this.userService.findUsersInProjectNotAssignedToTask(
      this.projectId,
      this.taskCode,
      this.formGroup.controls.username.value,
      this.page).then(response => {
      this.foundUsers = response.data;
      if(this.foundUsers.length == 6){
        this.page++;
      }
    });
  }

  searchUser() {
    console.log(this.formGroup.controls.username.value);
    this.userService.findUsersInProjectNotAssignedToTask(
      this.projectId,
      this.taskCode,
      this.formGroup.controls.username.value,
      this.page).then(response => {
      this.foundUsers = response.data;
      this.foundUsers = this.foundUsers.filter(u => {
        return !this.usersToAdd.find(u2 => u2.username === u.username);
      });
      //TODO dodać page++
    })
  }

  addUser(user: User) {
    this.usersToAdd.push(user);
    this.foundUsers = this.foundUsers.filter(u => u.username !== user.username);

  }

  removeUser(user: User) {
    this.usersToAdd = this.usersToAdd.filter(u => u.username !== user.username);
    this.foundUsers = [...this.foundUsers, user];
  }

  // TODO submit is send to parent
  close() {
    this.dialogRef.close();
  }


  addUsers() {
    this.dialogRef.close(this.usersToAdd);
  }
}
