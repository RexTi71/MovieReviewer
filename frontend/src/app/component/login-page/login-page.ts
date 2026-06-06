import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login-page',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  private http = inject(HttpClient);
  private route = inject(Router);
  errorMsg = signal('');
  isLoginBad = signal(false);
  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  tokenSave(token: string) {
    sessionStorage.setItem('token', token);
  }
  onLoginSubmit() {
    this.http
      .post('http://localhost:8080/api/auth/login', this.loginForm.value)
      .subscribe({
        next: (res) =>{
          this.isLoginBad.set(false);
          this.tokenSave(res as string);
          this.route.navigate(['/']);
        },
        error: (err) =>{
          this.isLoginBad.set( true);
          this.errorMsg.set(err.error);
        },
      });
  }
}
