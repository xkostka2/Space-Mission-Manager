import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AstronautHomePageComponent} from "./pages/astronaut-home-page/astronaut-home-page.component";
import {MyMissionsPageComponent} from "./pages/my-missions-page/my-missions-page.component";


const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: AstronautHomePageComponent
  },
  {
    path: 'myMissions',
    component: MyMissionsPageComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
