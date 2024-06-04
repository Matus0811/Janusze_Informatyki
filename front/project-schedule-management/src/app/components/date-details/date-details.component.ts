import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-date-details',
  templateUrl: './date-details.component.html',
  styleUrl: './date-details.component.css'
})
export class DateDetailsComponent {
  projectDate!: Date;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialogRef<DateDetailsComponent>
  ) {
    if(data){
      this.projectDate = new Date(data.finishDate);
    }
  }

}
