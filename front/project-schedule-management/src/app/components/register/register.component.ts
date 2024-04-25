import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm = new FormGroup({
    username: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    surname: new FormControl('',Validators.required),
    password: new FormControl('',Validators.minLength(8)),
    gender: new FormControl(''),
    email: new FormControl('',[Validators.required,
      Validators.email]),
    phone: new FormControl('',[Validators.required,
      Validators.pattern("^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")])
  });

  @Output() registerFormData = new EventEmitter();

  onRegisterSubmit(){
    if(this.registerForm.valid){
      this.registerFormData.emit(this.registerForm.value);
    }
  }
}
