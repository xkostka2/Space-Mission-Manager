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

  findAllMissions(): Observable<any>{
    return this.http.get(`${this.apiUrl}/missions`);
  }

  findMissionById(id: number): Observable<any>{
    return this.http.get(`${this.apiUrl}/missions/${id}`);
  }

  updateMission(mission: Mission): Observable<any>{
    return this.http.put(`${this.apiUrl}/missions/`, mission);
  }

  createMission(mission: Mission): Observable<any>{
    return this.http.post(`${this.apiUrl}/missions/`,mission);
  }

  removeMission(id: number): Observable<any>{
    return this.http.delete(`${this.apiUrl}/missions/${id}`);
  }

  archiveMission(id: number): Observable<any>{
    return this.http.get(`${this.apiUrl}/missions/${id}/archive`);
  }
}
