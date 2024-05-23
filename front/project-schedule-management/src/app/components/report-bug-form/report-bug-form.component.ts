import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-report-bug-form',
  templateUrl: './report-bug-form.component.html',
  styleUrl: './report-bug-form.component.css'
})
export class ReportBugFormComponent {
  reportBugForm = new FormGroup({
    title: new FormControl(""),
    description: new FormControl(""),
    bugType: new FormControl("")
  });

  constructor(private dialogRef: MatDialogRef<ReportBugFormComponent>) {}


  close() {
    this.dialogRef.close();
  }

  submitForm() {
    if(this.reportBugForm.valid){
      this.dialogRef.close(this.reportBugForm.value);
    }
  }
}
