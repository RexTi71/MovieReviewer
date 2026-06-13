import { Component, inject, input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Report } from '../../interface/report';

@Component({
  selector: 'app-feedback-actions',
  imports: [],
  templateUrl: './feedback-actions.html',
  styleUrl: './feedback-actions.css',
})
export class FeedbackActions {
  private http = inject(HttpClient);

  id= input('1');
  username = input('Nazwa użytkownika');
  content = input('Zawartość komentarza');
  date = input('2000-10-10');

  replyToUser() {}
  reportComment() {
    const report: Report = {
      username:this.username(),
      commentId: this.id(),
      content: this.content(),
      date: this.date()
    };
    this.http.post('http://localhost:8080/api/v1/report', report).subscribe({
      next: res =>{
        console.log(res);
      },
      error: err => {
        console.log(err);
      }
    });
  }
}
