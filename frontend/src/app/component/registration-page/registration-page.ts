import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-registration-page',
  imports: [ReactiveFormsModule],
  templateUrl: './registration-page.html',
  styleUrl: './registration-page.css',
})
export class RegistrationPage {
  private http = inject(HttpClient);
  registred = false;
  registrationForm = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
  });
  onRegistrationSubmit(){
    this.registred = true;
    console.log(this.registrationForm.value);
    this.http.post('http://localhost:8080/api/auth/register',this.registrationForm.value).subscribe((res) =>
    console.log("Successful registration"));
  }
}
