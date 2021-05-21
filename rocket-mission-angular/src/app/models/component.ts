import { ComponentType } from "./componentType";

export interface Component {
    id?: number;
    mission?: any;
    name: string;
    readyDate?: Date;
    readyToUse?: boolean;
    rocket?: any;
    type: ComponentType;
}
