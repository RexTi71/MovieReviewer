import { Component, inject, input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';

type AccountInfo = {
  username: string;
  email: string;
};
@Component({
  selector: 'app-profile-page',
  imports: [],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css',
})
export class ProfilePage {
  private http = inject(HttpClient);
  private token = sessionStorage.getItem('token');
  private route = inject(Router);
  accountInfo = toSignal(
    this.http.get<AccountInfo>(`http://localhost:8080/api/auth/me?token=${this.token}`)
  );
  onLoggingOut(){
    sessionStorage.removeItem('token');
    this.http.post(`http://localhost:8080/api/auth/logout?token=${this.token}`,'').subscribe((res) =>{
    console.log(res);
    });
    this.route.navigate(['/']);
  }
}
