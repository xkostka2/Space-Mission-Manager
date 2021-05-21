import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../models/user";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private REST_API_SERVER = 'http://localhost:8080/pa165/rest';

    constructor(
        private http: HttpClient,
    ) { }

    findAllUsers(): Observable<User[]> {
        return this.http.get(`${this.REST_API_SERVER}/users`) as Observable<User[]>;
    }

    findAllAstronauts(): Observable<User[]> {
        return this.http.get(`${this.REST_API_SERVER}/users/astronauts`) as Observable<User[]>;
    }

    findAllAvailableAstronauts(): Observable<User[]> {
        return this.http.get(`${this.REST_API_SERVER}/users/astronauts/available`) as Observable<User[]>;
    }

    findUserById(id: number): Observable<User> {
        return this.http.get(`${this.REST_API_SERVER}/users/${id}`) as Observable<User>;
    }

    // findUserByEmail not implemented

    createUser(user: User): Observable<User> {
        return this.http.post(`${this.REST_API_SERVER}/users/`, user) as Observable<User>;
    }

    updateUser(user: User): Observable<User> {
        return this.http.put(`${this.REST_API_SERVER}/users/`, user) as Observable<User>;
    }

    removeUser(id: number): Observable<object> {
        return this.http.delete(`${this.REST_API_SERVER}/users/${id}`);
    }

    acceptMission(id: number): Observable<User> {
        return this.http.get(`${this.REST_API_SERVER}/users/${id}/acceptMission`) as Observable<User>;
    }

    rejectMission(id: number, explanation: string): Observable<User> {
        const reqBody = {
            value: explanation
        };

        return this.http.post(`${this.REST_API_SERVER}/users/${id}/rejectMission`, reqBody) as Observable<User>;
    }
}
