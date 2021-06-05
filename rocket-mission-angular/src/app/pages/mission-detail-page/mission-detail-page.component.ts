import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MissionComponent } from 'src/app/models/component';
import { Mission } from 'src/app/models/mission';
import { MissionProgress } from 'src/app/models/missionProgress';
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
  plannedMission: boolean;
  astronautLogged: boolean;
  currentUserMission: Mission;

  constructor(
    private route: ActivatedRoute,
    private missionService: MissionsService,
    private authenticationService: AuthenticationService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.mission_id = params['id'];
    })

    this.missionService.findMissionById(this.mission_id).subscribe(x => {
      this.mission = x;

      this.plannedMission = this.mission.missionProgress === MissionProgress.Planned;
    });

    this.astronautLogged = this.authenticationService.getRole() === Role.Astronaut;
    this.currentUserMission = this.authenticationService.currentUser.mission;
  }

  startMission(): void {
    const updateMission = this.mission;
    updateMission.missionProgress = MissionProgress.InProgress;

    this.missionService.updateMission(updateMission).subscribe(x => {
      this.mission = x;
    });
    this.cd.detectChanges();
  }
}
