import {NgModule} from '@angular/core';
import {BrowserModule, provideClientHydration} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {LoginComponent} from './components/login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import {ProjectCardComponent} from './components/project-card/project-card.component';
import {ProjectContentComponent} from './components/project-content/project-content.component';
import {MenuComponent} from './components/menu/menu.component';
import {UserAuthComponent} from "./components/auth/user-auth.component";
import {RegisterComponent} from './components/register/register.component';
import {ProjectListComponent} from './components/project-list/project-list.component';
import {UserProjectsComponent} from './components/user-projects/user-projects.component';
import {MatDialogModule} from "@angular/material/dialog";
import {AddProjectFormComponent} from './components/add-project-form/add-project-form.component';
import {MatButton, MatButtonModule} from "@angular/material/button";
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from "@angular/material/datepicker";
import {provideNativeDateAdapter} from "@angular/material/core";
import {CurrentProjectViewComponent} from './components/current-project-view/current-project-view.component';
import {MatIconModule} from "@angular/material/icon";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { TaskListComponent } from './components/task-list/task-list.component';
import { ErrorHandlerComponent } from './components/error-handler/error-handler.component';
import { TaskDetailsComponent } from './components/task-details/task-details.component';
import { AddTaskFormComponent } from './components/add-task-form/add-task-form.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { AddCommentComponent } from './components/add-comment/add-comment.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserAuthComponent,
    ProjectCardComponent,
    ProjectContentComponent,
    MenuComponent,
    RegisterComponent,
    ProjectListComponent,
    UserProjectsComponent,
    AddProjectFormComponent,
    CurrentProjectViewComponent,
    TaskListComponent,
    ErrorHandlerComponent,
    TaskDetailsComponent,
    AddTaskFormComponent,
    CommentListComponent,
    AddCommentComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButton,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDatepicker,
    MatIconModule,
    MatButtonModule,
    MatMenuTrigger,
    MatMenu,
    BrowserAnimationsModule,
    MatMenuItem,
    AppRoutingModule,
  ],
  providers: [
    provideClientHydration(),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
