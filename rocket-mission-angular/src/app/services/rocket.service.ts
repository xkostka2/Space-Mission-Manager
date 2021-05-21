import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Rocket } from "../models/rocket";

@Injectable({
    providedIn: 'root'
})
export class RocketService {

    private REST_API_SERVER = 'http://localhost:8080/pa165/rest';

    constructor(
        private http: HttpClient,
    ) { }

    findAllRockets(): Observable<Rocket[]> {
        return this.http.get(`${this.REST_API_SERVER}/rockets`) as Observable<Rocket[]>;
    }

    getRocketById(id: number): Observable<Rocket> {
        return this.http.get(`${this.REST_API_SERVER}/rockets/${id}`) as Observable<Rocket>;
    }

    createRocket(rocket: Rocket): Observable<Rocket> {
        return this.http.post(`${this.REST_API_SERVER}/rockets/`, rocket) as Observable<Rocket>;
    }

    updateRocket(rocket: Rocket): Observable<Rocket> {
        return this.http.put(`${this.REST_API_SERVER}/rockets/`, rocket) as Observable<Rocket>;
    }

    removeRocket(id: number): Observable<Object> {
        return this.http.delete(`${this.REST_API_SERVER}/rockets/${id}`);
    }
}
