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
      breadcrumb: 'MENU_ITEMS.HOME'
    },
    children: [
      {
        path: '',
        component: AstronautHomePageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Astronaut,
          breadcrumb: 'MENU_ITEMS.HOME'
        }
      },
      {
        path: 'my-missions',
        component: MyMissionsPageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Astronaut,
          breadcrumb: 'MENU_ITEMS.MY_MISSIONS'
        },
        children: [
          {
            path: ':id',
            component: MissionDetailPageComponent,
            canActivate: [AuthGuard],
            data: {
              role: Role.Astronaut,
              breadcrumb: 'MENU_ITEMS.MISSION_DETAIL'
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
      breadcrumb: 'MENU_ITEMS.HOME'
    },
    children: [
      {
        path: '',
        component: ManagerHomePageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Manager,
          breadcrumb: 'MENU_ITEMS.HOME'
        }
      },
      {
        path: 'missions',
        component: MissionsPageComponent,
        canActivate: [AuthGuard],
        data: {
          role: Role.Manager,
          breadcrumb: 'MENU_ITEMS.MISSIONS'
        },
        children: [
          {
            path: ':id',
            component: MissionDetailPageComponent,
            canActivate: [AuthGuard],
            data: {
              role: Role.Manager,
              breadcrumb: 'MENU_ITEMS.MISSION_DETAIL'
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
          breadcrumb: 'MENU_ITEMS.ASTRONAUTS'
        },
        children: [
          {
            path: ':id',
            component: AstronautDetailPageComponent,
            canActivate: [AuthGuard],
            data: {
              role: Role.Manager,
              breadcrumb: 'MENU_ITEMS.ASTRONAUT_DETAIL'
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
          breadcrumb: 'MENU_ITEMS.COMPONENTS'
        }
      },
    ]
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
