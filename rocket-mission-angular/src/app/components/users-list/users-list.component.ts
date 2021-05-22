import { Component, Input, OnChanges } from "@angular/core";
import { MatTableDataSource } from "@angular/material";
import { User } from "src/app/models/user";

@Component({
    selector: 'app-users-list',
    templateUrl: './users-list.component.html',
    styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnChanges {

  @Input() users: User[];
  @Input() displayedColumns: String[];

  dataSource = new MatTableDataSource<User>();

  ngOnChanges(): void {
      this.dataSource = new MatTableDataSource<User>(this.users);
  }
}
