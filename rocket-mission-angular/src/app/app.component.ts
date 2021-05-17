import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(
    public authenticationService: AuthenticationService,
  ) {}

  headerColor = 'cadetblue';
  sideMenuColor = 'cadetblue';
  footerColor = 'cadetblue';

  currentYear = (new Date()).getFullYear();


  ngOnInit(): void {
    this.authenticationService.verifyAuth();
  }

  onLogout(): void {
    this.authenticationService.logout();
  }
}

