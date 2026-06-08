import { Component, inject} from '@angular/core';
import { DatePipe } from '@angular/common';
import {MovieReview} from '../movie-review/movie-review';
import { toSignal } from '@angular/core/rxjs-interop';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { ReviewAdd } from '../review-add/review-add';
import { Movie } from '../../interface/movie';
import { ReviewComment } from '../review-comment/review-comment';
import { Review } from '../../interface/review';

@Component({
  selector: 'app-movie-review-page',
  imports: [MovieReview, DatePipe, ReviewAdd, ReviewComment],
  templateUrl: './movie-review-page.html',
  styleUrl: './movie-review-page.css',
})
export class MovieReviewPage {
  token: string | null = sessionStorage.getItem('token');
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  id: string | null = this.route.snapshot.paramMap.get('id');
  movie = toSignal(this.http.get<Movie>(`http://localhost:8080/api/v1/movie/${this.id}`));
  reviews = toSignal(this.http.get<Review[]>(`http://localhost:8080/api/v1/reviews/${this.id}`));

  onClickScrollTo(el: HTMLElement) {
    el.scrollIntoView();
  }
}
