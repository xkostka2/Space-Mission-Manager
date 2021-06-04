import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MissionComponent } from 'src/app/models/component';
import { Mission } from 'src/app/models/mission';
import { Rocket } from 'src/app/models/rocket';
import { Role } from 'src/app/models/role';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { MissionsService } from 'src/app/services/missions.service';

@Component({
  selector: 'app-mission-detail-page',
  templateUrl: './mission-detail-page.component.html',
  styleUrls: ['./mission-detail-page.component.scss']
})
export class MissionDetailPageComponent implements OnInit {

  private mission_id: number;

  mission: Mission;
  astronautLogged: boolean;

  constructor(
    private route: ActivatedRoute,
    private missionService: MissionsService,
    private authenticationService: AuthenticationService,
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.mission_id = params['id'];
    })

    this.missionService.findMissionById(this.mission_id).subscribe(x => {
      this.mission = x;
    })

    this.astronautLogged = this.authenticationService.getRole() === Role.Astronaut;
  }

  getUsersList(users: User[]): string {
    return users.length === 0 ? "None" : users.map(x => x.name).join(", ");
  }

  getRocketsList(rockets: Rocket[]): string {
    return rockets.length === 0 ? "None" : rockets.map(x => x.name).join(", ");
  }

  getComponentsList(components: MissionComponent[]): string {
    return components.length === 0 ? "None" : components.map(x => x.name).join(", ");
  }
}
