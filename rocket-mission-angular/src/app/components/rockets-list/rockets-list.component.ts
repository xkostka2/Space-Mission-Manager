import {Component, Input, OnChanges} from "@angular/core";
import {MatTableDataSource} from "@angular/material";
import {Rocket} from "src/app/models/rocket";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-rockets-list',
  templateUrl: './rockets-list.component.html',
  styleUrls: ['./rockets-list.component.scss']
})
export class RocketsListComponent implements OnChanges {

  @Input() rockets: Rocket[];

  @Input()
  hiddenColumns: string[] = []

  @Input()
  selection = new SelectionModel<Rocket>(true, []);

  displayedColumns = ['select', 'id', 'name', 'mission', 'requiredComponents'];

  dataSource = new MatTableDataSource<Rocket>();

  ngOnChanges(): void {
    this.displayedColumns = this.displayedColumns.filter(x => !this.hiddenColumns.includes(x));
    this.dataSource = new MatTableDataSource<Rocket>(this.rockets);
  }

  getRequiredComponentsList(rocket: Rocket): string {
    return rocket.requiredComponents.map(x => x.name).join(", ");
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

  checkboxLabel(row?: Rocket): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }
}
