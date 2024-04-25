import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{
  loginForm = new FormGroup({
    login: new FormControl("",Validators.required),
    password: new FormControl("",Validators.required),
  });


  @Output() loginData = new EventEmitter();

  onLoginSubmit(){
    if(this.loginForm.valid){
      this.loginData.emit(this.loginForm.value);
    }
  }

}
