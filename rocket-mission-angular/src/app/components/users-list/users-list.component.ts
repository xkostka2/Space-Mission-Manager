import { Component, Input, OnChanges } from "@angular/core";
import { MatTableDataSource } from "@angular/material";
import { User } from "src/app/models/user";
import {MissionComponent} from "../../models/component";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
    selector: 'app-users-list',
    templateUrl: './users-list.component.html',
    styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnChanges {

  @Input() users: User[];

  @Input()
  hiddenColumns = [];

  @Input()
  selection = new SelectionModel<MissionComponent>(true, []);

  displayedColumns: string[] = ['select', 'id', 'name', 'email', 'levelOfExperience', 'mission', 'missionExplanation'];
  dataSource = new MatTableDataSource<User>();

  ngOnChanges(): void {
    this.displayedColumns = this.displayedColumns.filter(x => !this.hiddenColumns.includes(x));
    this.dataSource = new MatTableDataSource<User>(this.users);
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

  checkboxLabel(row?: User): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }
}
