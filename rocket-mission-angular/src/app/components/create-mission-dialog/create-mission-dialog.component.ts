import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MissionComponent} from "../../models/component";
import {User} from "../../models/user";
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
  templateUrl: './create-mission-dialog.component.html',
  styleUrls: ['./create-mission-dialog.component.scss']
})
export class CreateMissionDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<CreateMissionDialogComponent>,
              private _formBuilder: FormBuilder,
              private componentService: ComponentService,
              private userService: UserService,
              private missionsService:MissionsService,
              private rocketService:RocketService) { }

  detailsFormGroup:FormGroup = null;

  components: MissionComponent[] = [];
  astronauts: User[] = [];
  rockets: Rocket[] = [];

  componentSelection = new SelectionModel<MissionComponent>(true, []);
  astronautSelection = new SelectionModel<User>(true, []);
  rocketSelection = new SelectionModel<Rocket>(true, []);

  loading: boolean;

  ngOnInit() {
    this.detailsFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
      destinationCtrl: ['', Validators.required],
      etaCtrl: ['', Validators.required]
    });

    this.componentService.getAvailableComponents().subscribe(components => {
      this.components = components.filter(component => component.readyToUse && component.mission == null && component.type == ComponentType.Rocket)
    });
    this.userService.findAllAvailableAstronauts().subscribe(astronauts => {
      this.astronauts = astronauts;
    })
    this.rocketService.findAllRockets().subscribe(rockets => {
      this.rockets = rockets.filter(r => !r.mission);
    })
  }

  onAdd() {
    this.loading = true;
    const mission: Mission = {
      name: this.detailsFormGroup.get('nameCtrl').value,
      destination: this.detailsFormGroup.get('destinationCtrl').value,
      eta: this.detailsFormGroup.get('etaCtrl').value,
      missionProgress: MissionProgress.Planned,
      users: this.astronautSelection.selected,
      rockets: this.rocketSelection.selected,
      components: this.componentSelection.selected
    }
    this.missionsService.createMission(mission).subscribe(() => {
      this.loading = false;
      this.dialogRef.close(true)
    })
  }

  onCancel() {
    this.dialogRef.close(false)
  }
}
