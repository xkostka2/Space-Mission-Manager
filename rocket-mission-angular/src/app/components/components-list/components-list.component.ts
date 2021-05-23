import {Component, Input, OnChanges} from "@angular/core";
import {MatTableDataSource} from "@angular/material";
import {MissionComponent} from "src/app/models/component";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-components-list',
  templateUrl: './components-list.component.html',
  styleUrls: ['./components-list.component.scss']
})
export class ComponentsListComponent implements OnChanges {

  @Input() components: MissionComponent[];

  @Input()
  hiddenColumns: string[] = [];

  @Input()
  selection = new SelectionModel<MissionComponent>(true, []);

  displayedColumns = ['select', 'id', 'name', 'mission', 'readyDate', 'readyToUse', 'rocket', 'type'];

  dataSource = new MatTableDataSource<MissionComponent>();

  ngOnChanges(): void {
    this.displayedColumns = this.displayedColumns.filter(x => !this.hiddenColumns.includes(x));
    this.dataSource = new MatTableDataSource<MissionComponent>(this.components);
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
