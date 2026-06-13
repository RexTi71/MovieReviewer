import { Component, inject, input, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import {CommentAdd} from '../../interface/comment-add';
import { Review } from '../../interface/review';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { CommentResponse } from '../../interface/comment-response';
import { ReviewComment } from '../review-comment/review-comment';
import { filter, map, switchMap, take } from 'rxjs';
import { FeedbackActions } from '../feedback-actions/feedback-actions';
import { ReportService } from '../../service/report-service';


@Component({
  selector: 'app-movie-review',
  imports: [ReactiveFormsModule, ReviewComment, FeedbackActions],
  templateUrl: './movie-review.html',
  styleUrl: './movie-review.css',
})
export class MovieReview {
  private http = inject(HttpClient);
  private activeRoute = inject(ActivatedRoute);
  private reportSerivce = inject(ReportService);

  token = sessionStorage.getItem('token');

  review = input<Review>();
  title = input('Tytuł');
  rating = input('3');
  username = input('Nazwa użytkownika');
  content = input('zawartość recenzji');
  imageUrl = input('def-avatar.png');
  replyUsername = this.reportSerivce.replyUsername;

  commentAddForm = new FormGroup({
    content: new FormControl(''),
  });
  private review$ = toObservable(this.review);

  comments = toSignal(
    this.review$.pipe(
      filter((rev): rev is Review => !!rev?.userId && !!rev?.movieId),
      switchMap(({ userId, movieId }) => {
        const params = new HttpParams().set('accountId', userId!).set('movieId', movieId!);
        return this.http.get<CommentResponse[]>(`http://localhost:8080/api/v1/comment`, { params });
      }),
      map((allComments) => {
        const roots = allComments.filter((c) => !c.parentId);
        const replies = allComments.filter((c) => c.parentId);
        return roots.flatMap((root) => [
          root,
          ...replies.filter((reply) => reply.parentId === root.id),
        ]);
      }),
    ),
    { initialValue: [] },
  );

  onSubmit() {
    this.review$.pipe(take(1)).subscribe((review) => {
      if (!review) return;
      const comment: CommentAdd = {
        content: this.commentAddForm.get('content')?.value,
        token: this.token,
        movieId: this.activeRoute.snapshot.paramMap.get('id'),
        reviewAccountId: review.userId,
        parentId: this.reportSerivce.replyCommentId(),
      };
      this.http.post(`http://localhost:8080/api/v1/comment`, comment).subscribe({
        next: (res) => {
          console.log(res);
          this.reportSerivce.clearReply();
        },
        error: (err) => {
          console.log(err.error);
        },
      });
    });
  }
}
