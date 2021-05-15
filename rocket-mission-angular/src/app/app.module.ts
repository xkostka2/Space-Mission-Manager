import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import { AstronautHomePageComponent } from './pages/astronaut-home-page/astronaut-home-page.component';
import { MyMissionsPageComponent } from './pages/my-missions-page/my-missions-page.component';
import { MissionDetailPageComponent } from './pages/mission-detail-page/mission-detail-page.component';
import { ManagerHomePageComponent } from './pages/manager-home-page/manager-home-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { MissionsPageComponent } from './pages/missions-page/missions-page.component';
import { AstronautsPageComponent } from './pages/astronauts-page/astronauts-page.component';
import { ComponentsPageComponent } from './pages/components-page/components-page.component';
import { AstronautDetailPageComponent } from './pages/astronaut-detail-page/astronaut-detail-page.component';

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    AstronautHomePageComponent,
    MyMissionsPageComponent,
    MissionDetailPageComponent,
    ManagerHomePageComponent,
    LoginPageComponent,
    NotFoundPageComponent,
    MissionsPageComponent,
    AstronautsPageComponent,
    ComponentsPageComponent,
    AstronautDetailPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private translate: TranslateService) {
    this.translate.setDefaultLang('en');
    this.translate.use('en');
  }
}
