import {MissionProgress} from "./missionProgress";
import {User} from "./user";
import {Rocket} from "./rocket";
import {MissionComponent} from "./component";

export interface Mission {
  id?: number;
  name: string;
  destination?: string;
  missionProgress?: MissionProgress;
  users?: User[];
  rockets?: Rocket[];
  components?: MissionComponent[];
  eta?: Date;
  finishedDate?: Date;
  startedDate?: Date;
  result?: string;
}
