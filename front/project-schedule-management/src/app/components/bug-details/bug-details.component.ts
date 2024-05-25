import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Bug} from "../../domain/bug";

@Component({
  selector: 'app-bug-details',
  templateUrl: './bug-details.component.html',
  styleUrl: './bug-details.component.css'
})
export class BugDetailsComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public bug: Bug, private dialogRef: MatDialogRef<BugDetailsComponent>) {
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
