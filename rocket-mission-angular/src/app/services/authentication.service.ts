import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Role} from '../models/role';
import {User} from '../models/user';
import {Router} from "@angular/router";
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import {MissionComponent} from "../models/component";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  currentUser: User;

  private REST_API_SERVER = 'http://localhost:8080/pa165/rest';

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  loginUser(email: string, password: string): Observable<Object> {
    const authDto = {
      email: email,
      password: password
    }

    return this.http.post(`${this.REST_API_SERVER}/auth/login`, authDto)
      .pipe(
        catchError(x => {
          return of(false);
        })
    );
  }

  login(user: User): void {
    this.currentUser = user;
    const storedUser = JSON.stringify(this.currentUser);
    localStorage.setItem('auth:user', storedUser);
    this.redirectToOriginDestination();
  }

  refreshUser():  Observable<User> {
    return this.http.get(`${this.REST_API_SERVER}/users/${this.currentUser.id}`) as Observable<User>;
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

    if (!redirectUrl || redirectUrl === '' || redirectUrl === 'pa165') {
      redirectUrl = this.getRole() === Role.Astronaut ? '/astronaut' : '/manager';
    }
    if(redirectUrl.startsWith('/astronaut') && this.getRole() !== Role.Astronaut){
      redirectUrl = '/manager'+redirectUrl.split('/').splice(0,1).join('/')
    }
    if(redirectUrl.startsWith('/manager') && this.getRole() !== Role.Manager){
      redirectUrl = '/astronaut'+redirectUrl.split('/').splice(0,1).join('/')
    }
    this.router.navigate([redirectUrl], {replaceUrl: true})
  }
}
