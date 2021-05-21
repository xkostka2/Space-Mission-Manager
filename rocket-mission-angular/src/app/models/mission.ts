import {MissionProgress} from "./missionProgress";
import {User} from "./user";
import {Rocket} from "./rocket";
import {Component} from "./component";

export interface Mission {
  id: number;
  name: string;
  destination: string;
  missionProgress: MissionProgress;
  users?: User[];
  rockets?: Rocket[];
  components?: Component[];
  eta: Date;
  finishedDate: Date;
  startedDate: Date;
  result: string;
}
