import {Component, Input, OnChanges} from '@angular/core';
import {Mission} from "../../models/mission";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-missions-list',
  templateUrl: './missions-list.component.html',
  styleUrls: ['./missions-list.component.scss']
})
export class MissionsListComponent implements OnChanges {

  @Input() missions: Mission[] = [];
  @Input() displayedColumns: String[];

  dataSource = new MatTableDataSource<Mission>()

  ngOnChanges() {
    this.dataSource = new MatTableDataSource<Mission>(this.missions);
  }

  getUsersList(mission: Mission): string {
    return mission.users.map(x => x.name).join(", ");
  }

  getRocketsList(mission: Mission): string {
    return mission.rockets.map(x => x.name).join(", ");
  }

  getComponentsList(mission: Mission): string {
    return mission.components.map(x => x.name).join(", ");
  }
}
