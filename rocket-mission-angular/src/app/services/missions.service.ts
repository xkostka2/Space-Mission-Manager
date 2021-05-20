import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Mission} from "../models/mission";

@Injectable({
  providedIn: 'root'
})
export class MissionsService {

  apiUrl: string = 'http://localhost:8080/pa165/rest';
  headers = new HttpHeaders().set('Content-Type', 'application/json').set("Access-Control-Allow-Origin", "*").set("Access-Control-Allow-Methods",
    "GET, POST, PUT, DELETE, OPTIONS");

  constructor(private http: HttpClient) { }

  findAllMissions(): Observable<any>{
    return this.http.get(`${this.apiUrl}/missions`, {headers: this.headers});
  }

  findMissionById(id: number): Observable<any>{
    return this.http.get(`${this.apiUrl}/missions/${id}`, {headers: this.headers});
  }

  updateMission(mission: Mission): Observable<any>{
    return this.http.put(`${this.apiUrl}/missions/`, mission,{headers: this.headers});
  }

  createMission(mission: Mission): Observable<any>{
    return this.http.post(`${this.apiUrl}/missions/`,mission,  {headers: this.headers});
  }

  removeMission(id: number): Observable<any>{
    return this.http.delete(`${this.apiUrl}/missions/${id}`, {headers: this.headers});
  }

  archiveMission(id: number): Observable<any>{
    return this.http.get(`${this.apiUrl}/missions/${id}/archive`, {headers: this.headers});
  }
}
