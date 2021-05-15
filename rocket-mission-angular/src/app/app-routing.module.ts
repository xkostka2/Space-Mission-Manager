import { componentFactoryName } from '@angular/compiler';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AstronautDetailPageComponent } from './pages/astronaut-detail-page/astronaut-detail-page.component';
import {AstronautHomePageComponent} from "./pages/astronaut-home-page/astronaut-home-page.component";
import { AstronautsPageComponent } from './pages/astronauts-page/astronauts-page.component';
import { ComponentsPageComponent } from './pages/components-page/components-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { ManagerHomePageComponent } from './pages/manager-home-page/manager-home-page.component';
import { MissionDetailPageComponent } from './pages/mission-detail-page/mission-detail-page.component';
import { MissionsPageComponent } from './pages/missions-page/missions-page.component';
import {MyMissionsPageComponent} from "./pages/my-missions-page/my-missions-page.component";
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    component: LoginPageComponent
  },
  {
    path: 'home',
    component: AstronautHomePageComponent
  },
  {
    path: 'home/manager',
    component: ManagerHomePageComponent
  },
  {
    path: 'myMissions',
    component: MyMissionsPageComponent
  },
  {
    path: 'missions',
    component: MissionsPageComponent
  },
  {
    path: 'mission/:id',
    component: MissionDetailPageComponent
  },
  {
    path: 'astronauts',
    component: AstronautsPageComponent
  },
  {
    path: 'astronaut/:id',
    component: AstronautDetailPageComponent
  },
  {
    path: 'components',
    component: ComponentsPageComponent
  },
  {
    path: '**',
    component: NotFoundPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
