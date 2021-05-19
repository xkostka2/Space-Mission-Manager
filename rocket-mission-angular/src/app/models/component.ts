import { ComponentType } from "./componentType";

export interface Component {
    id: number;
    readyToUse?: boolean;
    name: string;
    componentType: ComponentType;
    readyDate: Date;
    // mission: Mission;
    // rocket: Rocket;
}