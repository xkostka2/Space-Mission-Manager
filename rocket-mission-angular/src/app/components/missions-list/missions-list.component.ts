import {Component, Input, OnChanges} from '@angular/core';
import {Mission} from "../../models/mission";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-missions-list',
  templateUrl: './missions-list.component.html',
  styleUrls: ['./missions-list.component.scss']
})
export class MissionsListComponent implements OnChanges {

  @Input()
  missions: Mission[] = [];

  @Input()
  hiddenColumns = [];

  displayedColumns: string[] = ['id', 'name', 'destination', 'missionProgress', 'acceptReject', 'eta','startedDate', 'finishedDate'];
  dataSource = new MatTableDataSource<Mission>()

  ngOnChanges() {
    this.displayedColumns = this.displayedColumns.filter(x => !this.hiddenColumns.includes(x));
    this.dataSource = new MatTableDataSource<Mission>(this.missions);
  }

}
