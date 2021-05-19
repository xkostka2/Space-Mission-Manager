import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Role} from '../models/role';
import {User} from '../models/user';
import {Router} from "@angular/router";
import {LevelOfExperience} from "../models/levelOfExperience";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  currentUser: User;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  login(username: string, password: string): boolean {
    if (true) { // HTTP request
      this.currentUser = {
        id: 1,
        name: "John",
        email: "john@gmail.com",
        password: "tralala123",
        role: Role.Astronaut,
        levelOfExperience: LevelOfExperience.Rookie,
        mission: {id: 42},
        missionAccepted: true,
        missionExplanation: null
      }
      const storedUser = JSON.stringify(this.currentUser);
      localStorage.setItem('auth:user', storedUser);
      this.redirectToOriginDestination();
      return true
    }

    this.currentUser = null;
    return false;
  }

  logout(): void {
    this.currentUser = null;
    localStorage.removeItem('auth:user');
    this.router.navigate(['']);
  }

  getRole(): Role | null {
    if (this.currentUser) {
      return this.currentUser.role;
    }
    return null;
  }

  isLoggedIn(): boolean {
    return this.currentUser !== null && this.currentUser !== undefined;
  }

  verifyAuth() {
    const storedUser = localStorage.getItem('auth:user');
    if(!this.isLoggedIn()){
      if(storedUser){
        this.currentUser = JSON.parse(storedUser);
      } else {
        sessionStorage.setItem('auth:redirect', location.pathname);
        this.router.navigate([])
      }
    }
  }

  redirectToOriginDestination(){
    let redirectUrl = sessionStorage.getItem('auth:redirect');
    sessionStorage.removeItem('auth:redirect')

    if (!redirectUrl || redirectUrl === '') {
      redirectUrl = this.getRole() === Role.Astronaut ? '/astronaut' : '/manager';
    }
    this.router.navigate([redirectUrl], {replaceUrl: true})
  }
}
