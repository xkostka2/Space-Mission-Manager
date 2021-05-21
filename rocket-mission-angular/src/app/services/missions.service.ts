import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Mission} from "../models/mission";

@Injectable({
  providedIn: 'root'
})
export class MissionsService {

  apiUrl: string = 'http://localhost:8080/pa165/rest';

  constructor(private http: HttpClient) { }

  findAllMissions(): Observable<Mission[]>{
    return this.http.get(`${this.apiUrl}/missions`) as Observable<Mission[]>;
  }

  findMissionById(id: number): Observable<Mission>{
    return this.http.get(`${this.apiUrl}/missions/${id}`) as Observable<Mission>;
  }

  updateMission(mission: Mission): Observable<Mission>{
    return this.http.put(`${this.apiUrl}/missions/`, mission) as Observable<Mission>;
  }

  createMission(mission: Mission): Observable<Mission>{
    return this.http.post(`${this.apiUrl}/missions/`,mission) as Observable<Mission>;
  }

  removeMission(id: number): Observable<any>{
    return this.http.delete(`${this.apiUrl}/missions/${id}`);
  }

  archiveMission(id: number): Observable<any>{
    return this.http.get(`${this.apiUrl}/missions/${id}/archive`);
  }
}
