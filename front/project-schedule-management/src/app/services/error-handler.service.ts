import { Injectable } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {GlobalErrorHandlerComponent} from "../components/global-error-handler/global-error-handler.component";

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {
  constructor(private dialog:MatDialog) { }

  handle(data: any) {
    this.dialog.open(GlobalErrorHandlerComponent,{
      data: data
    })
  }
}
