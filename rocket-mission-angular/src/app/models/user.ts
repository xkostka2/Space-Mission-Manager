import { LevelOfExperience } from "./levelOfExperience";
import { Role } from "./role";
import {Mission} from "./mission";

export interface User {
    id: number;
    name: string;
    email: string;
    password: string;
    role: Role;
    levelOfExperience: LevelOfExperience;
    mission?: Mission;
    missionAccepted?: boolean;
    missionExplanation?: string;
}
