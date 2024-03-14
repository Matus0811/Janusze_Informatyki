import { Component, OnInit } from '@angular/core';
import { User } from './user/user';
import { UserService } from './user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'project-schedule-management';

  public user!: User;

  constructor(private userService: UserService){}
  
  ngOnInit(): void {
    this.getUser(1);
  }

  getUser(id: number){
    this.userService.getUser(id).subscribe(
      {
        next: (v) => this.user = v,
        error: (e) => console.log(e)
      }
    );
  }
}
