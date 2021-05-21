import {Component, OnInit} from '@angular/core';
import {MissionsService} from "../../services/missions.service";
import {Mission} from "../../models/mission";
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-astronaut-home-page',
  templateUrl: './astronaut-home-page.component.html',
  styleUrls: ['./astronaut-home-page.component.scss']
})
export class AstronautHomePageComponent implements OnInit {

  constructor(private missionsService: MissionsService,
              private authenticationService: AuthenticationService) { }

  missions: Mission[] = [];
  loading: boolean;
  user: User;

  ngOnInit() {
    this.loading = true;
    this.user = this.authenticationService.currentUser;
    this.missionsService.findAllMissions().subscribe(missions => {
      this.missions = missions;
      this.loading = false;
    })
  }

}
