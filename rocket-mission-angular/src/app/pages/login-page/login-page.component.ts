import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
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
    email: new FormControl(null),
    password: new FormControl(null),
  })

  onSubmit(): void {
    this.loginForm.get('email').setErrors(null);
    this.loginForm.get('password').setErrors(null);
    this.loginForm.updateValueAndValidity();

    if (this.loginForm.valid) {
      const email = this.loginForm.get('email').value;
      const password = this.loginForm.get('password').value;

      this.authenticationService.loginUser(email, password).subscribe(
        user => {
          if (user) {
            this.authenticationService.login(user as User);
            return;
          }
          this.loginForm.get('email').setErrors({'incorrect': true});
          this.loginForm.get('password').setErrors({'incorrect': true});
        }
      );
    }
  }
}
