import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { MissionComponent } from "../models/component";

@Injectable({
    providedIn: 'root'
})
export class ComponentService {

    private REST_API_SERVER = 'http://localhost:8080/pa165/rest';

    constructor(
        private http: HttpClient,
    ) { }

    findAllComponents(): Observable<MissionComponent[]> {
        return this.http.get(`${this.REST_API_SERVER}/components`) as Observable<MissionComponent[]>;
    }

    getAvailableComponents(): Observable<MissionComponent[]> {
        return this.http.get(`${this.REST_API_SERVER}/components/available`) as Observable<MissionComponent[]>;
    }

    getComponentById(id: number): Observable<MissionComponent> {
        return this.http.get(`${this.REST_API_SERVER}/components/${id}`) as Observable<MissionComponent>;
    }

    createComponent(component: MissionComponent): Observable<MissionComponent> {
        return this.http.post(`${this.REST_API_SERVER}/components/`, component) as Observable<MissionComponent>;
    }

    updateComponent(component: MissionComponent): Observable<MissionComponent> {
        return this.http.put(`${this.REST_API_SERVER}/components/`, component) as Observable<MissionComponent>;
    }

    removeComponent(id: number): Observable<Object> {
        return this.http.delete(`${this.REST_API_SERVER}/components/${id}`);
    }
}
