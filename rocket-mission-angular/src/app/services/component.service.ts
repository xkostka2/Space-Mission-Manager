import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Component } from "../models/component";

@Injectable({
    providedIn: 'root'
})
export class ComponentService {

    private REST_API_SERVER = 'http://localhost:8080/pa165/rest';

    constructor(
        private http: HttpClient,
    ) { }

    findAllComponents(): Observable<Component[]> {
        return this.http.get(`${this.REST_API_SERVER}/components`) as Observable<Component[]>;
    }

    getAvailableComponents(): Observable<Component[]> {
        return this.http.get(`${this.REST_API_SERVER}/components/available`) as Observable<Component[]>;
    }

    getComponentById(id: number): Observable<Component> {
        return this.http.get(`${this.REST_API_SERVER}/components/${id}`) as Observable<Component>;
    }

    createComponent(component: Component): Observable<Component> {
        return this.http.post(`${this.REST_API_SERVER}/components/`, component) as Observable<Component>;
    }

    updateComponent(component: Component): Observable<Component> {
        return this.http.put(`${this.REST_API_SERVER}/components/`, component) as Observable<Component>;
    }

    removeComponent(id: number): Observable<Object> {
        return this.http.delete(`${this.REST_API_SERVER}/components/${id}`);
    }
}
