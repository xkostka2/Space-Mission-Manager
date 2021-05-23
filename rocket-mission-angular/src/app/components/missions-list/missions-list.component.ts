import {Component, Input, OnChanges} from '@angular/core';
import {Mission} from "../../models/mission";
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {MissionComponent} from "../../models/component";

@Component({
  selector: 'app-missions-list',
  templateUrl: './missions-list.component.html',
  styleUrls: ['./missions-list.component.scss']
})
export class MissionsListComponent implements OnChanges {

  @Input()
  selection = new SelectionModel<MissionComponent>(true, []);

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

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected == numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  checkboxLabel(row?: MissionComponent): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }
}
