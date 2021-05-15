import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Role } from '../models/role';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  
  currentUser: User;

  constructor(
    private http: HttpClient,
  ) { }

  login(username: string, password: string): boolean {
    if (true) { // HTTP request
      this.currentUser = {
        name: username,
        password: password,
        role: Role.Astronaut, //TODO
      }

      return true
    }

    this.currentUser = null;
    return false;
  }

  logout(): void {
    this.currentUser = null;
  }

  getRole(): Role | null {
    if (this.currentUser) {
      return this.currentUser.role;
    }
    return null;
  }
}
