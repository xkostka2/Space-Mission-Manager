import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MissionComponent} from "../../models/component";
import {ComponentService} from "../../services/component.service";
import {UserService} from "../../services/user.service";
import {SelectionModel} from "@angular/cdk/collections";
import {MissionsService} from "../../services/missions.service";
import {Mission} from "../../models/mission";
import {MissionProgress} from "../../models/missionProgress";
import {Rocket} from "../../models/rocket";
import {RocketService} from "../../services/rocket.service";
import {ComponentType} from "../../models/componentType";

@Component({
  selector: 'app-create-mission-dialog',
  templateUrl: './create-rocket-dialog.component.html',
  styleUrls: ['./create-rocket-dialog.component.scss']
})
export class CreateRocketDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<CreateRocketDialogComponent>,
              private _formBuilder: FormBuilder,
              private componentService: ComponentService,
              private userService: UserService,
              private missionsService:MissionsService,
              private rocketService:RocketService) { }

  detailsFormGroup:FormGroup = null;

  components: MissionComponent[] = [];
  missions: Mission[] = [];

  componentSelection = new SelectionModel<MissionComponent>(true, []);

  missionSelection = new SelectionModel<Mission>(false, []);

  loading: boolean;

  ngOnInit() {
    this.detailsFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required]
    });

    this.componentService.getAvailableComponents().subscribe(components => {
      this.components = components.filter(component => component.readyToUse && component.type == ComponentType.Mission && component.rocket == null);
    });

    this.missionsService.findAllMissions().subscribe(missions => {
      this.missions = missions.filter(mission => mission.missionProgress != MissionProgress.Finished && mission.rockets.length == 0)
    })
  }

  onAdd() {
    this.loading = true;

    const rocket: Rocket = {
      name: this.detailsFormGroup.get('nameCtrl').value,
      mission: this.missionSelection.selected[0],
      requiredComponents: this.componentSelection.selected
    }
    this.rocketService.createRocket(rocket).subscribe(() => {
      this.loading = false;
      this.dialogRef.close(true)
    })
  }

  onCancel() {
    this.dialogRef.close(false)
  }
}
