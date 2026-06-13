import { Component, inject, input, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import {CommentAdd} from '../../interface/comment-add';
import { Review } from '../../interface/review';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { CommentResponse } from '../../interface/comment-response';
import { ReviewComment } from '../review-comment/review-comment';
import { filter, switchMap } from 'rxjs';
import { FeedbackActions } from '../feedback-actions/feedback-actions';


@Component({
  selector: 'app-movie-review',
  imports: [ReactiveFormsModule, ReviewComment, FeedbackActions],
  templateUrl: './movie-review.html',
  styleUrl: './movie-review.css',
})
export class MovieReview {
  private http = inject(HttpClient);
  private activeRoute = inject(ActivatedRoute);
  token = sessionStorage.getItem('token');

  review = input<Review>();
  title = input('Tytuł');
  rating = input('3');
  username = input('Nazwa użytkownika');
  content = input('zawartość recenzji');
  imageUrl = input('def-avatar.png');

  commentAddForm = new FormGroup({
    content: new FormControl(''),
  });
  private review$ = toObservable(this.review);

  comments = toSignal(
    this.review$.pipe(
      filter((rev): rev is Review => !!rev && !!rev.userId && !!rev.movieId),
      switchMap((rev) =>
        this.http.get<CommentResponse[]>(
          `http://localhost:8080/api/v1/comment?accountId=${rev.userId}&movieId=${rev.movieId}`,
        ),
      ),
    ),
    { initialValue: [] },
  );

  onSubmit() {
    const comment: CommentAdd = {
      content: this.commentAddForm.get('content')?.value,
      token: this.token,
      movieId: this.activeRoute.snapshot.paramMap.get('id'),
    };
    this.http.post(`http://localhost:8080/api/v1/comment`, comment).subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => {
        console.log(err.error);
      },
    });
  }
}
