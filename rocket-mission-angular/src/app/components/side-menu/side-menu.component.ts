import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {Role} from "../../models/role";

interface SideMenuItem {
  label: string;
  url: string;
  activeRegex: string;
}

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss']
})
export class SideMenuComponent implements OnChanges {

  private currentUrl: string;

  constructor(
    private router: Router
  ) {
    this.currentUrl = router.url;

    router.events.subscribe((_: NavigationEnd) => {
      if (_ instanceof NavigationEnd) {
        this.currentUrl = _.url;
      }
    });
  }

  @Input()
  userRole: Role;

  items: SideMenuItem[] = [];

  ngOnInit() {
  }

  isActive(regexValue: string) {
    const regexp = new RegExp(regexValue);

    return regexp.test(this.currentUrl);
  }

  ngOnChanges(changes: SimpleChanges): void {
    const prefix = this.userRole ? '/astronaut' : '/manager'
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

}

