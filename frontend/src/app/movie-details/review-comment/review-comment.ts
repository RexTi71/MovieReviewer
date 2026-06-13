import { Component, input } from '@angular/core';
import { DatePipe, NgOptimizedImage } from '@angular/common';
import { FeedbackActions } from '../feedback-actions/feedback-actions';

@Component({
  selector: 'app-review-comment',
  imports: [NgOptimizedImage, DatePipe, FeedbackActions],
  templateUrl: './review-comment.html',
  styleUrl: './review-comment.css',
})
export class ReviewComment {
  token = sessionStorage.getItem('token');
  id= input('1');
  avatarUrl = input('');
  username = input('Nazwa użytkownika');
  content = input('Zawartość komentarza');
  date = input('2000-10-10');

}
