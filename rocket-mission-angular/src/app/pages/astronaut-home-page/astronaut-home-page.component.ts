import {Component, OnInit} from '@angular/core';
import {MissionsService} from "../../services/missions.service";
import {Mission} from "../../models/mission";
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-astronaut-home-page',
  templateUrl: './astronaut-home-page.component.html',
  styleUrls: ['./astronaut-home-page.component.scss']
})
export class AstronautHomePageComponent implements OnInit {

  constructor(private missionsService: MissionsService,
              private authenticationService: AuthenticationService,
              private userService : UserService) { }

  missions: Mission[] = [];
  loading: boolean;
  user: User;

  ngOnInit() {
    this.loadData()
  }

  loadData() {
    this.loading = true;
    this.user = this.authenticationService.currentUser;
    this.missionsService.findAllMissions().subscribe(missions => {
      if (this.user.mission != null && !this.user.missionAccepted) {
        this.missions = missions.filter(e => e.users.find(e => e.id == this.user.id))
      }
      this.loading = false;
    })
  }


  acceptMission() {
    this.userService.acceptMission(this.user.id).subscribe(user => {

      this.user = user
      this.user.missionAccepted = true
      this.authenticationService.currentUser.missionAccepted = true
      this.missions = []
    })
  }
}
