import { Component, OnInit } from '@angular/core';
import { Mission } from 'src/app/models/mission';
import { User } from 'src/app/models/user';
import { Component as Comp } from 'src/app/models/component';
import { ComponentService } from 'src/app/services/component.service';
import { MissionsService } from 'src/app/services/missions.service';
import { RocketService } from 'src/app/services/rocket.service';
import { UserService } from 'src/app/services/user.service';
import { Rocket } from 'src/app/models/rocket';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-manager-home-page',
  templateUrl: './manager-home-page.component.html',
  styleUrls: ['./manager-home-page.component.scss']
})
export class ManagerHomePageComponent implements OnInit {

  astronauts: User[];
  missions: Mission[];
  components: Comp[];
  rockets: Rocket[];

  astronautsDataSource = new MatTableDataSource<User>();

  constructor(
    private userService: UserService,
    private rocketService: RocketService,
    private componentService: ComponentService,
    private missionService: MissionsService
  ) { }

  ngOnInit() {
    this.userService.findAllAstronauts().subscribe(data => {
      this.astronauts = data;
    });

    this.missionService.findAllMissions().subscribe(data => {
      this.missions = data;
    });

    this.componentService.findAllComponents().subscribe(data => {
      this.components = data;
    });

    this.rocketService.findAllRockets().subscribe(data => {
      this.rockets = data;
    });
  }
}
