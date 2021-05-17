import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';

interface BreadcrumbItem {
  label: string;
  routerLink: string;
}

@Component({
  selector: 'breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.scss']
})
export class BreadcrumbsComponent implements OnInit {

  static readonly ROUTE_DATA_BREADCRUMB = 'breadcrumb';

  menuItems: BreadcrumbItem[] = [];
  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private translate: TranslateService) {}

  ngOnInit(): void {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.menuItems = [];
        this.createBreadcrumbs(this.activatedRoute.root)
      });
  }

  private createBreadcrumbs(route: ActivatedRoute, routerLink: string = '') {
    const children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
      return;
    }

    children.forEach(child =>  {
      const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
      if (routeURL !== '') {
        routerLink += `/${routeURL}`;
      }

      if(!child.snapshot.data.hasOwnProperty(BreadcrumbsComponent.ROUTE_DATA_BREADCRUMB)) {
        return ;
      }
      const label = this.translate.instant(child.snapshot.data[BreadcrumbsComponent.ROUTE_DATA_BREADCRUMB]);
      if (label && (!this.menuItems[this.menuItems.length-1] || label !== this.menuItems[this.menuItems.length-1].label)) {
        this.menuItems.push({label, routerLink});
      }

      return this.createBreadcrumbs(child, routerLink);
    });
  }
}
