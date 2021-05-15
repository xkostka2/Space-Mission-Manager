import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit{

  private currentUrl: string;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    ) {
    this.currentUrl = router.url;

    router.events.subscribe((_: NavigationEnd) => {
      if (_ instanceof NavigationEnd) {
        this.currentUrl = _.url;
      }
    });
  }

  headerColor = 'cadetblue';
  sideMenuColor = 'cadetblue';
  footerColor = 'cadetblue';

  currentYear = (new Date()).getFullYear();
  items: SideMenuItem[] = [];

  ngOnInit(): void {
    this.items = [
      {
        label: 'MENU_ITEMS.HOME',
        url: '/home',
        activeRegex: '^/home$'
      },
      {
        label: 'MENU_ITEMS.MY_MISSIONS',
        url: '/myMissions',
        activeRegex: '^/myMissions$'
      }
    ]
  }

  isActive(regexValue: string) {
    const regexp = new RegExp(regexValue);

    return regexp.test(this.currentUrl);
  }

  onLogout(): void {
    this.authenticationService.logout();
    this.router.navigate(['']);
  }
}

interface SideMenuItem {
  label: string;
  url: string;
  activeRegex: string;
}
