import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';
import { Component } from "../models/component";

@Injectable({
    providedIn: 'root'
})
export class ComponentService {

    private REST_API_SERVER = 'http://localhost:8080/pa165/rest';

    constructor(
        private http: HttpClient,
    ) { }

    getComponents(): Observable<Component[]> {
        return this.http.get(`${this.REST_API_SERVER}/components`)
            .pipe(
                map((res: any[]) => {
                    const data: Component[] = [];
                    
                    res.forEach(x => {
                        data.push(
                            {
                                id: x.id,
                                readyToUse: x.readyToUse,
                                name: x.name,
                                readyDate: x.readyDate,
                                componentType: x.type
                            }
                        )
                    })

                    return data;
                })
            );
    }
}
