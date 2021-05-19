import { Component, OnInit } from '@angular/core';
import { ComponentService } from 'src/app/services/component.service';

@Component({
  selector: 'app-components-page',
  templateUrl: './components-page.component.html',
  styleUrls: ['./components-page.component.scss']
})
export class ComponentsPageComponent implements OnInit {

  components: Component[] = []

  constructor(
    private componentService: ComponentService,
  ) { }

  ngOnInit() {
    this.componentService.getComponents().subscribe((data: any[]) => {
      this.components = data;
    });
  }

}
