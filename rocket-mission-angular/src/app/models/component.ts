import { ComponentType } from "./componentType";
import { Mission } from "./mission";
import { Rocket } from "./rocket";

export interface Component {
    id?: number;
    mission?: Mission;
    name: string;
    readyDate?: Date;
    readyToUse?: boolean;
    rocket?: Rocket;
    type?: ComponentType;
}
