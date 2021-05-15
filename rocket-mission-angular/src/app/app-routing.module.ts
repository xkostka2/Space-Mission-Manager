import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
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
    component: LoginPageComponent,
    pathMatch: 'full',
  },
  {
    path: 'astronaut',
    component: AstronautHomePageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'astronaut/my-missions',
    component: MyMissionsPageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'astronaut/mission/:id',
    component: MissionDetailPageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'manager',
    component: ManagerHomePageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'manager/missions',
    component: MissionsPageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'manager/mission/:id',
    component: MissionDetailPageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'manager/astronauts',
    component: AstronautsPageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'manager/astronaut/:id',
    component: AstronautDetailPageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'manager/components',
    component: ComponentsPageComponent,
    canActivate: [AuthGuard],
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
