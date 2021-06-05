import {Component, Input, OnChanges} from '@angular/core';
import {Mission} from "../../models/mission";
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {MissionComponent} from "../../models/component";
import {AuthenticationService} from "../../services/authentication.service";
import {Role} from "../../models/role";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ArchiveMissionDialogComponent} from "../archive-mission-dialog/archive-mission-dialog.component";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {AstronautHomePageComponent} from "../../pages/astronaut-home-page/astronaut-home-page.component";
import {RejectMissionDialogComponent} from "../reject-mission-dialog/reject-mission-dialog.component";

@Component({
  selector: 'app-missions-list',
  templateUrl: './missions-list.component.html',
  styleUrls: ['./missions-list.component.scss']
})
export class MissionsListComponent implements OnChanges {

  constructor(private authenticationService:AuthenticationService,
              private userService:UserService,
              private dialog: MatDialog,
              private astronautHomePageComp: AstronautHomePageComponent) {
  }

  @Input()
  selection = new SelectionModel<MissionComponent>(false, []);

  @Input()
  missions: Mission[] = [];

  @Input()
  hiddenColumns = [];

  user: User;

  displayedColumns: string[] = ['id', 'name', 'destination', 'missionProgress', 'acceptReject', 'eta','startedDate', 'finishedDate', 'isArchived'];

  dataSource = new MatTableDataSource<Mission>()

  ngOnInit() {
    this.user = this.authenticationService.currentUser;
  }

  ngOnChanges() {
    if(this.authenticationService.getRole() === Role.Manager){
      this.displayedColumns.push('archive')
    }

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

  archive(mission: Mission) {
    const config = new MatDialogConfig();
    config.disableClose = true;
    config.autoFocus = false;
    config.width = '450px';
    config.data = mission.id;

    this.dialog.open(ArchiveMissionDialogComponent, config);
  }

  accept() {
    this.astronautHomePageComp.acceptMission()
  }

  reject() {
    const config = new MatDialogConfig();
    config.disableClose = true;
    config.autoFocus = false;
    config.width = '450px';
    config.data = this.user.id

    this.dialog.open(RejectMissionDialogComponent, config);

  }
}
