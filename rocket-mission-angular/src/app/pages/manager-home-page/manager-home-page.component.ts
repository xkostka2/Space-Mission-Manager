import { Component, OnInit } from '@angular/core';
import { Mission } from 'src/app/models/mission';
import { User } from 'src/app/models/user';
import { MissionComponent } from 'src/app/models/component';
import { ComponentService } from 'src/app/services/component.service';
import { MissionsService } from 'src/app/services/missions.service';
import { RocketService } from 'src/app/services/rocket.service';
import { UserService } from 'src/app/services/user.service';
import { Rocket } from 'src/app/models/rocket';
import { MatTableDataSource } from '@angular/material';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-manager-home-page',
  templateUrl: './manager-home-page.component.html',
  styleUrls: ['./manager-home-page.component.scss']
})
export class ManagerHomePageComponent implements OnInit {

  astronauts: User[];
  missions: Mission[];
  components: MissionComponent[];
  rockets: Rocket[];

  astronautsLoading: boolean;
  missionsLoading: boolean;
  componentsLoading: boolean;
  rocketsLoading: boolean;

  astronautsDataSource = new MatTableDataSource<User>();

  constructor(
    private userService: UserService,
    private rocketService: RocketService,
    private componentService: ComponentService,
    private missionService: MissionsService
  ) { }

  ngOnInit(): void {
    this.loadAstronauts();
    this.loadMissions();
    this.loadComponents();
    this.loadRockets();
  }

  private loadAstronauts(): void {
    this.astronautsLoading = true;
    this.userService.findAllAstronauts().subscribe(data => {
      this.astronauts = data;
      this.astronautsLoading = false;
    });
  }

  private loadMissions(): void {
    this.missionsLoading = true;
    this.missionService.findAllMissions().subscribe(data => {
      this.missions = data;
      this.missionsLoading = false;
    });
  }

  private loadComponents(): void {
    this.componentsLoading = true;
    this.componentService.findAllComponents().subscribe(data => {
      this.components = data;
      this.componentsLoading = false;
    });
  }

  private loadRockets(): void {
    this.rocketsLoading = true;
    this.rocketService.findAllRockets().subscribe(data => {
      this.rockets = data;
      this.rocketsLoading = false;
    });
  }
}
