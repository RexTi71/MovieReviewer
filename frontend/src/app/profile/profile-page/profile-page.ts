import { Component, inject, signal } from '@angular/core';
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
  token = sessionStorage.getItem('token');
  private route = inject(Router);
  fileName = '';

  accountInfo = toSignal(
    this.http.get<AccountInfo>(`http://localhost:8080/api/auth/me?token=${this.token}`),
  );

  avatarUrl = signal(`http://localhost:8080/api/avatar?token=${this.token}`);

  onFileUpload(event: Event) {

    const target = event.target as HTMLInputElement;

    if (target.files == null) {
      return;
    }
    const file = target.files[0];

    if (file) {
      this.fileName = file.name;
      const formData = new FormData();

      formData.append('file', file);

      const upload$ = this.http.post(
        `http://localhost:8080/api/avatar?token=${this.token}`,
        formData,
      );

      upload$.subscribe();
    }
  }
  onLoggingOut() {
    sessionStorage.removeItem('token');
    this.http
      .post(`http://localhost:8080/api/auth/logout?token=${this.token}`, '')
      .subscribe((res) => {
        console.log(res);
      });
    this.route.navigate(['/'], {state: {loggedOut: true}});
  }
}
