import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectContentComponent} from "./components/project-content/project-content.component";
import {UserAuthComponent} from "./components/auth/user-auth.component";
import {authGuard} from "./guards/auth.guard";
import {UserProjectsComponent} from "./components/user-projects/user-projects.component";
import {CurrentProjectViewComponent} from "./components/current-project-view/current-project-view.component";

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
    children: [
      {
        path: 'my-projects/project/:id',
        component: CurrentProjectViewComponent
      },
      {
        path: 'my-projects',
        component: UserProjectsComponent,
        // children: [

        // ]
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
