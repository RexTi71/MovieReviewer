import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login-page',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  private http = inject(HttpClient);
  private route = inject(Router);
  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  tokenSave(token: string){
    sessionStorage.setItem('token', token);
  }
  onLoginSubmit(){
    this.http.post('http://localhost:8080/api/auth/login', this.loginForm.value).subscribe((res) =>{
      this.tokenSave(res as string);
      this.route.navigate(['/']);
    });

  }

}
