import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
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
import {RejectMissionDialogComponent} from "../reject-mission-dialog/reject-mission-dialog.component";

@Component({
  selector: 'app-missions-list',
  templateUrl: './missions-list.component.html',
  styleUrls: ['./missions-list.component.scss']
})
export class MissionsListComponent implements OnChanges, OnInit {

  constructor(private authenticationService:AuthenticationService,
              private userService:UserService,
              private dialog: MatDialog) {
  }

  @Input()
  selection = new SelectionModel<MissionComponent>(false, []);

  @Input()
  missions: Mission[] = [];

  @Input()
  hiddenColumns = [];

  user: User;

  @Input()
  disableRouting = false;

  @Output()
  refreshPage = new EventEmitter<boolean>();

  displayedColumns: string[] = ['select', 'id', 'name', 'destination', 'missionProgress', 'eta','startedDate', 'finishedDate', 'isArchived', 'acceptReject'];

  dataSource = new MatTableDataSource<Mission>()

  ngOnInit() {
    this.user = this.authenticationService.currentUser;
  }

  isManager: boolean;

  ngOnChanges() {
    if(this.authenticationService.getRole() === Role.Manager){
      this.displayedColumns.push('archive')
      this.isManager = true;
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

    const dialogRef = this.dialog.open(ArchiveMissionDialogComponent, config);
    dialogRef.afterClosed().subscribe(res => {
      if(res){
       this.refreshPage.emit(true);
      }
    })
  }

  accept() {
    this.userService.acceptMission(this.user.id).subscribe(user => {

      this.user = user
      this.user.missionAccepted = true
      this.authenticationService.currentUser.missionAccepted = true
      this.missions = []
    })
  }

  reject() {
    const config = new MatDialogConfig();
    config.disableClose = true;
    config.autoFocus = false;
    config.width = '450px';
    config.data = this.user.id

    const dialogRef = this.dialog.open(RejectMissionDialogComponent, config);

    dialogRef.afterClosed().subscribe(res => {
      console.log(this.authenticationService.currentUser)
      this.user = this.authenticationService.currentUser
      this.missions = []
      this.refreshPage.emit(true);
    })
  }
}
