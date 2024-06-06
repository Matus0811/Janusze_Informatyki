import { Injectable } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {GlobalErrorHandlerComponent} from "../components/global-error-handler/global-error-handler.component";
import {MessageHandlerComponent} from "../components/message-handler/message-handler.component";

@Injectable({
  providedIn: 'root'
})
export class MessageHandlerService {
  constructor(private dialog:MatDialog) { }

  handleException(data: any) {
    this.dialog.open(GlobalErrorHandlerComponent,{
      data: data
    })
  }

  handleMessageInfo(data:any){
    this.dialog.open(MessageHandlerComponent,{
      data: data
    })
  }

}
