import { Component } from "./component";
import { Mission } from "./mission";

export class Rocket {
    id?: string;
    name: string;
    mission?: Mission;
    requiredComponents?: Component[];
}
