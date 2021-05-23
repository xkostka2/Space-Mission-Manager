import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { LoginRedirectGuard } from './guards/login-redirect.guard';
import { Role } from './models/role';
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
import {HomePageComponent} from "./components/home-page/home-page.component";
import {RocketsPageComponent} from "./pages/rockets-page/rockets-page.component";


const routes: Routes = [
  {
    path: '',
    component: LoginPageComponent,
    pathMatch: 'full',
    canActivate: [LoginRedirectGuard]
  },
  {
    path: 'astronaut',
    component: HomePageComponent,
    canActivate: [AuthGuard],
    data: {
      role: Role.Astronaut,
      breadcrumb: 'Home'
    },
    children: [
      {
        path: '',
        component: AstronautHomePageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Astronaut,
          breadcrumb: 'Home'
        }
      },
      {
        path: 'my-missions',
        component: MyMissionsPageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Astronaut,
          breadcrumb: 'My missions'
        },
        children: [
          {
            path: ':id',
            component: MissionDetailPageComponent,
            canActivate: [AuthGuard],
            data: {
              role: Role.Astronaut,
              breadcrumb: 'Mission detail'
            }
          }
        ]
      },
    ]
  },
  {
    path: 'manager',
    component: HomePageComponent,
    canActivate: [AuthGuard],
    data: {
      role: Role.Manager,
      breadcrumb: 'Home'
    },
    children: [
      {
        path: '',
        component: ManagerHomePageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Manager,
          breadcrumb: 'Home'
        }
      },
      {
        path: 'missions',
        component: MissionsPageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Manager,
          breadcrumb: 'Missions'
        },
        children: [
          {
            path: ':id',
            component: MissionDetailPageComponent,
            canActivate: [AuthGuard],
            data: {
              role: Role.Manager,
              breadcrumb: 'Mission detail'
            }
          }
        ]
      },
      {
        path: 'astronauts',
        component: AstronautsPageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Manager,
          breadcrumb: 'Astronauts'
        },
        children: [
          {
            path: ':id',
            component: AstronautDetailPageComponent,
            canActivate: [AuthGuard],
            data: {
              role: Role.Manager,
              breadcrumb: 'Astronaut detail'
            }
          },
        ]
      },
      {
        path: 'components',
        component: ComponentsPageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Manager,
          breadcrumb: 'Components'
        }
      },
      {
        path: 'rockets',
        component: RocketsPageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Manager,
          breadcrumb: 'Rockets'
        }
      },
    ]
  },
  { // bug fix
    path: 'pa165',
    redirectTo: 'astronaut',
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
