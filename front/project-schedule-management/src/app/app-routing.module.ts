import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectContentComponent} from "./components/project-content/project-content.component";
import {UserAuthComponent} from "./components/auth/user-auth.component";
import {authGuard} from "./guards/auth.guard";
import {UserProjectsComponent} from "./components/user-projects/user-projects.component";
import {CurrentProjectViewComponent} from "./components/current-project-view/current-project-view.component";
import {TaskListComponent} from "./components/task-list/task-list.component";
import {TaskDetailsComponent} from "./components/task-details/task-details.component";
import {ProjectUserListComponent} from "./components/project-user-list/project-user-list.component";
import {ProjectsMemberComponent} from "./components/projects-member/projects-member.component";
import {ProjectMemberViewComponent} from "./components/project-member-view/project-member-view.component";
import {ProjectStatusDetailsComponent} from "./components/project-status-details/project-status-details.component";

const routes: Routes = [
  {
    path: "auth/login",
    component: UserAuthComponent
  },
  {
    path: 'projects',
    redirectTo: 'projects/my-projects'
  },
  {
    path: "projects",
    component: ProjectContentComponent,
    canActivate: [authGuard],
    canActivateChild: [authGuard],
    children: [
      {
        path: 'my-projects/project/:id/task/:id',
        component: TaskDetailsComponent
      },

      {
        path: 'my-projects/project/:id',
        component: CurrentProjectViewComponent,
        children: [
          {
            path: 'tasks',
            component: TaskListComponent
          },
          {
            path: 'users',
            component: ProjectUserListComponent
          },
          {
            path: 'project-status',
            component: ProjectStatusDetailsComponent
          }
        ]
      },
      {
        path: 'projects-member/project/:id',
        component: ProjectMemberViewComponent
      },

      {
        path: 'projects-member',
        component: ProjectsMemberComponent,
        children: []
      },
      {
        path: 'my-projects',
        component: UserProjectsComponent
      }
    ]
  },
  {
    path: '',
    redirectTo: "/projects",
    pathMatch: "full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
