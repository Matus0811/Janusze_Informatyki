import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {ProjectService} from "../../services/project.service";
import {ProjectForm} from "../../domain/project-form";

@Component({
  selector: 'app-add-project-form',
  templateUrl: './add-project-form.component.html',
  styleUrl: './add-project-form.component.css'
})
export class AddProjectFormComponent {
  projectForm: FormGroup = new FormGroup({
    name: new FormControl('',Validators.required),
    description: new FormControl('', Validators.required),
    finishDate: new FormControl('')
  });


  constructor(private dialogRef: MatDialogRef<AddProjectFormComponent>) {
  }

  submitForm(){
    if(this.projectForm.valid){
      this.dialogRef.close(this.projectForm.value);
    }
  }

  close() {
    this.dialogRef.close();
  }
}
