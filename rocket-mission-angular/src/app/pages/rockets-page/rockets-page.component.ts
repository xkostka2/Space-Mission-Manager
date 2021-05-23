import { Component, OnInit } from '@angular/core';
import {Rocket} from "../../models/rocket";
import {RocketService} from "../../services/rocket.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {CreateMissionDialogComponent} from "../../components/create-mission-dialog/create-mission-dialog.component";
import {MissionsService} from "../../services/missions.service";
import {CreateRocketDialogComponent} from "../../components/create-rocket-dialog/create-rocket-dialog.component";

@Component({
  selector: 'app-rockets-page',
  templateUrl: './rockets-page.component.html',
  styleUrls: ['./rockets-page.component.scss']
})
export class RocketsPageComponent implements OnInit {

  rockets: Rocket[] = []
  loading: boolean;

  constructor(
    private rocketService: RocketService,
    private dialog: MatDialog
  ) { }

  loadData() {
    this.loading = true
    this.rocketService.findAllRockets().subscribe((data: any[]) => {
      this.rockets = data;
      this.loading = false
    });
  }

  ngOnInit() {
    this.loadData()
  }

  createRocket() {
    const config = new MatDialogConfig();
    config.disableClose = true;
    config.autoFocus = false;
    config.width = '750px';

    const result = this.dialog.open(CreateRocketDialogComponent, config);
    result.afterClosed().subscribe(res => {
      if (res){
        this.loadData();
      }
    })

  }
}
