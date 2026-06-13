import { inject, Injectable, signal } from '@angular/core';
import { Report } from '../interface/report';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  private http = inject(HttpClient);
  replyCommentId = signal<string | null>(null);
  replyUsername = signal<string | null>(null);

  setReply(id: string, username: string) {
    this.replyCommentId.set(id);
    this.replyUsername.set(username);
  }

  clearReply() {
    this.replyCommentId.set(null);
    this.replyUsername.set(null);
  }
  addReportForComment(report: Report) {
    this.http.post('http://localhost:8080/api/v1/report', report).subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
