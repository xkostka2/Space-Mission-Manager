import { LevelOfExperience } from "./levelOfExperience";
import { Role } from "./role";

export interface User {
    id?: number;
    name: string;
    password: string;
    role?: Role;
    levelOfExperience?: LevelOfExperience;
}