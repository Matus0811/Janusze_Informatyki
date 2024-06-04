import {Component, Inject, Input} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-global-error-handler',
  templateUrl: './global-error-handler.component.html',
  styleUrl: './global-error-handler.component.css'
})
export class GlobalErrorHandlerComponent {
  errorMessage="";
  constructor(
    @Inject(MAT_DIALOG_DATA) public responseError:any,
    private dialogRef: MatDialogRef<GlobalErrorHandlerComponent>
  ) {
    console.log(responseError);
    this.errorMessage = responseError.data;
  }

  closeModule() {
    this.dialogRef.close();
  }
}
