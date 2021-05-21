import {Component, Input, OnChanges} from '@angular/core';
import {Mission} from "../../models/mission";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-missions-list',
  templateUrl: './missions-list.component.html',
  styleUrls: ['./missions-list.component.scss']
})
export class MissionsListComponent implements OnChanges {

  constructor() { }

  @Input()
  missions: Mission[] = [];

  displayedColumns: string[] = ['id', 'name', 'destination', 'missionProgress', 'acceptReject'];
  dataSource = new MatTableDataSource<Mission>()

  ngOnChanges() {
    this.dataSource = new MatTableDataSource<Mission>(this.missions);
  }

}
