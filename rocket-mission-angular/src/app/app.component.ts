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
    public authenticationService: AuthenticationService,
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
      // const prefix = this.authenticationService.currentUser.role ? '/astronaut': '/manager'
    const prefix = '/astronaut';
      this.items = [
        {
          label: 'MENU_ITEMS.HOME',
          url: prefix,
          activeRegex: `^${prefix}$`
        },
        {
          label: 'MENU_ITEMS.MY_MISSIONS',
          url: `${prefix}/my-missions`,
          activeRegex: `^${prefix}/my-missions$`
        },
        {
          label: 'MENU_ITEMS.MISSION_DETAIL',
          url: `${prefix}/my-missions/42`,
          activeRegex: `^${prefix}/my-missions/42$`
        }
      ];
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
