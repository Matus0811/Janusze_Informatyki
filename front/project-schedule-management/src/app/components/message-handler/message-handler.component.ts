import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-message-handler',
  templateUrl: './message-handler.component.html',
  styleUrl: './message-handler.component.css'
})
export class MessageHandlerComponent {
  message = "";

  constructor(
    @Inject(MAT_DIALOG_DATA) private messageData:any,
    private dialogRef: MatDialogRef<MessageHandlerComponent>
  ) {
    this.message = this.messageData;
  }

  closeModule() {
    this.dialogRef.close();
  }
}
