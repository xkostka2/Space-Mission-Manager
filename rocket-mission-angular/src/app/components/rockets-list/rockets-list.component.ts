import { Component, Input, OnChanges } from "@angular/core";
import { MatTableDataSource } from "@angular/material";
import { Rocket } from "src/app/models/rocket";

@Component({
    selector: 'app-rockets-list',
    templateUrl: './rockets-list.component.html',
    styleUrls: ['./rockets-list.component.scss']
})
export class RocketsListComponent implements OnChanges {

    @Input() rockets: Rocket[];
    @Input() displayedColumns: String[];

    dataSource = new MatTableDataSource<Rocket>();

    ngOnChanges(): void {
        this.dataSource = new MatTableDataSource<Rocket>(this.rockets);
    }

    getRequiredComponentsList(rocket: Rocket): string {
        return rocket.requiredComponents.map(x => x.name).join(", ");
      }
}
