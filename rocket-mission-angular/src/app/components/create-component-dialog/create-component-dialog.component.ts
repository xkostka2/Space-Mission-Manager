import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MissionComponent} from "../../models/component";
import {ComponentService} from "../../services/component.service";


@Component({
  selector: 'app-create-component-dialog',
  templateUrl: './create-component-dialog.component.html',
  styleUrls: ['./create-component-dialog.component.scss']
})
export class CreateComponentDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<CreateComponentDialogComponent>,
              private _formBuilder: FormBuilder,
              private componentService: ComponentService
              ) { }

  detailsFormGroup:FormGroup = null;

  loading: boolean;

  ngOnInit() {
    this.detailsFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
      typeCtrl: ['', Validators.required],
      readyDateCtrl: ['', Validators.required]
    });
  }

  onAdd() {
    this.loading = true;
    const component: MissionComponent = {
      name: this.detailsFormGroup.get('nameCtrl').value,
      type: this.detailsFormGroup.get('typeCtrl').value.toUpperCase(),
      readyDate: this.detailsFormGroup.get('readyDateCtrl').value,
      readyToUse: false
    }
    this.componentService.createComponent(component).subscribe(() => {
      this.loading = false;
      this.dialogRef.close()
      window.location.reload();
    })
  }

  onCancel() {
    this.dialogRef.close(false)
  }
}
