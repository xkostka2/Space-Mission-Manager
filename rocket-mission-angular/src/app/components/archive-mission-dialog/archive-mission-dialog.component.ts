import {Component, Inject} from '@angular/core';
import {MissionsService} from "../../services/missions.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-archive-mission-dialog',
  templateUrl: './archive-mission-dialog.component.html',
  styleUrls: ['./archive-mission-dialog.component.scss']
})
export class ArchiveMissionDialogComponent {

  constructor(private missionsService:MissionsService,
              private dialogRef: MatDialogRef<ArchiveMissionDialogComponent>,
              @Inject(MAT_DIALOG_DATA) private data: number,
              ) { }

  commentControl = new FormControl('', Validators.required);
  loading: boolean;

  onClose(){
    this.dialogRef.close(false)
  }

  onArchive(){
    this.loading = true;
    this.missionsService.archiveMission(this.data,this.commentControl.value).subscribe(res => {
      this.loading = false;
      this.dialogRef.close(true)
    })
  }

}
