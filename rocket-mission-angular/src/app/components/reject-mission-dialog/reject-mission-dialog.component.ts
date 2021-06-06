import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-reject-mission-dialog',
  templateUrl: './reject-mission-dialog.component.html',
  styleUrls: ['./reject-mission-dialog.component.scss']
})
export class RejectMissionDialogComponent {

  constructor(private userService: UserService,
              private dialogRef: MatDialogRef<RejectMissionDialogComponent>,
              private authenticationService: AuthenticationService,
              @Inject(MAT_DIALOG_DATA) private data: number,
              ) { }

  commentControl = new FormControl('', Validators.required);
  loading: boolean;

  onClose(){
    this.dialogRef.close(false)
  }

  onReject(){
    this.loading = true;
    this.userService.rejectMission(this.data,this.commentControl.value).subscribe(res => {
      console.log("mission rejected", res);
      this.authenticationService.currentUser.mission = null
      this.authenticationService.currentUser.missionExplanation = this.commentControl.value
      this.loading = false
      this.dialogRef.close(true)
    })

  }
}
