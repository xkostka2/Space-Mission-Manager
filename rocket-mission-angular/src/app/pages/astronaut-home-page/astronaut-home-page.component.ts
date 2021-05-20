import { Component, OnInit } from '@angular/core';
import {MissionsService} from "../../services/missions.service";
import {Mission} from "../../models/mission";

@Component({
  selector: 'app-astronaut-home-page',
  templateUrl: './astronaut-home-page.component.html',
  styleUrls: ['./astronaut-home-page.component.scss']
})
export class AstronautHomePageComponent implements OnInit {

  constructor(private  missionsService:MissionsService) { }

  missions: Mission[] = [];
  loading: boolean;

  ngOnInit() {
    this.loading = true;
    this.missionsService.findAllMissions().subscribe(missions => {
      this.missions = missions;
      this.loading = false;
    })
  }

}
