import { Component, OnInit } from '@angular/core';
import {MissionsService} from "../../services/missions.service";
import {Mission} from "../../models/mission";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {CreateMissionDialogComponent} from "../../components/create-mission-dialog/create-mission-dialog.component";

@Component({
  selector: 'app-missions-page',
  templateUrl: './missions-page.component.html',
  styleUrls: ['./missions-page.component.scss']
})
export class MissionsPageComponent implements OnInit {

  constructor(private missionsService: MissionsService,
              private dialog: MatDialog) { }

  missions: Mission[] = [];
  loading: boolean;

  ngOnInit() {
    this.loadData()
  }

  loadData(){
    this.loading = true;
    this.missionsService.findAllMissions().subscribe(missions => {
      this.missions = missions;
      this.loading = false;
    })
  }

  createMission() {
    const config = new MatDialogConfig();
    config.disableClose = true;
    config.autoFocus = false;
    config.width = '750px';

    const result = this.dialog.open(CreateMissionDialogComponent, config);
    result.afterClosed().subscribe(res => {
      if (res){
        this.loadData();
      }
    })

  }

  deleteMission() {

  }
}
