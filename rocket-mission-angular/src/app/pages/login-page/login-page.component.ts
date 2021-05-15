import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Role } from 'src/app/models/role';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
  ) { }

  ngOnInit() {
  }

  loginForm: FormGroup = new FormGroup({
    username: new FormControl(null),
    password: new FormControl(null),
  })

  onSubmit(): void {
    this.loginForm.updateValueAndValidity();

    if (this.loginForm.valid) {
      const username = this.loginForm.get('username').value;
      const password = this.loginForm.get('password').value;

      if (this.authenticationService.login(username, password)) {
        if (this.authenticationService.getRole() === Role.Astronaut) {
          this.router.navigate(['astronaut']);
          return;
        }
        this.router.navigate(['manager']);
      }
    }
  }
}
