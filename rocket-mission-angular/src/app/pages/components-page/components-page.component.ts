import { Component, OnInit } from '@angular/core';
import { ComponentService } from 'src/app/services/component.service';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {CreateComponentDialogComponent} from "../../components/create-component-dialog/create-component-dialog.component";

@Component({
  selector: 'app-components-page',
  templateUrl: './components-page.component.html',
  styleUrls: ['./components-page.component.scss']
})
export class ComponentsPageComponent implements OnInit {

  components: Component[] = []
  loading: boolean;

  constructor(private componentService: ComponentService,
              private dialog: MatDialog) { }

  ngOnInit() {
    this.loadData()
  }

  private loadData() {
    this.loading = true
    this.componentService.findAllComponents().subscribe((data: any[]) => {
      this.components = data;
      this.loading = false
    });
  }

  createComponent() {
    const config = new MatDialogConfig();
    config.disableClose = true;
    config.autoFocus = false;
    config.width = '750px';

    const result = this.dialog.open(CreateComponentDialogComponent, config);
    result.afterClosed().subscribe(res => {
      if (res){
        this.loadData();
      }
    })

  }
}
