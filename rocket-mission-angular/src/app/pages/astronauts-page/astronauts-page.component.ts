import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-astronauts-page',
  templateUrl: './astronauts-page.component.html',
  styleUrls: ['./astronauts-page.component.scss']
})
export class AstronautsPageComponent implements OnInit {

  constructor(private userService: UserService,) { }

  astronauts: User[] = [];
  loading: boolean;

  ngOnInit() {
    this.loadData()
  }

  loadData(){
    this.loading = true;
    this.userService.findAllAstronauts().subscribe(astronauts => {
      this.astronauts = astronauts
      this.loading = false;
    })
  }
}
