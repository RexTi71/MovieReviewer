import { Component, inject, input } from '@angular/core';
import { Report } from '../../interface/report';
import { ReportService } from '../../service/report-service';

@Component({
  selector: 'app-feedback-actions',
  imports: [],
  templateUrl: './feedback-actions.html',
  styleUrl: './feedback-actions.css',
})
export class FeedbackActions {
  private reportService = inject(ReportService);

  id = input('1');
  username = input('Nazwa użytkownika');
  content = input('Zawartość komentarza');
  date = input('2000-10-10');



  replyToUser(){
    this.reportService.setReply(this.id(), this.username());
  };
  reportComment() {
    const report: Report = {
      username: this.username(),
      commentId: this.id(),
      content: this.content(),
      date: this.date(),
    };
    this.reportService.addReportForComment(report);
  }
}
