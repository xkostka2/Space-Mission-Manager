import { LevelOfExperience } from "./levelOfExperience";
import { Role } from "./role";

export interface User {
    id?: number;
    name: string;
    email: string;
    password: string;
    role: Role;
    levelOfExperience?: LevelOfExperience;
    mission?: any;
    missionAccepted?: boolean;
    missionExplanation?: string;
}
