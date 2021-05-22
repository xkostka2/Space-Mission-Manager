import { Component, Input, OnChanges } from "@angular/core";
import { MatTableDataSource } from "@angular/material";
import { Component as Comp } from "src/app/models/component";

@Component({
    selector: 'app-components-list',
    templateUrl: './components-list.component.html',
    styleUrls: ['./components-list.component.scss']
  })
  export class ComponentsListComponent implements OnChanges {

    @Input() components: Comp[];
    @Input() displayedColumns: String[];

    dataSource = new MatTableDataSource<Comp>();

    ngOnChanges(): void {
        this.dataSource = new MatTableDataSource<Comp>(this.components);
    }
  }
