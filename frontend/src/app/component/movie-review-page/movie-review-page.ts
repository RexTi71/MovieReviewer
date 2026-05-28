import { Component, inject, signal, Signal } from '@angular/core';
import { DatePipe, NgOptimizedImage} from '@angular/common';
import {MovieReview} from '../movie-review/movie-review';
import { ReviewComment } from '../review-comment/review-comment';
import { toSignal } from '@angular/core/rxjs-interop';
import { Movie } from '../home-page/home-page';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-movie-review-page',
  imports: [NgOptimizedImage, MovieReview, ReviewComment, DatePipe],
  templateUrl: './movie-review-page.html',
  styleUrl: './movie-review-page.css',
})
export class MovieReviewPage {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  movie = toSignal(
    this.route.paramMap.pipe(
      switchMap((params) => {
        const id = params.get('id');
        return this.http.get<Movie>(`http://localhost:8080/api/v1/movie/${id}`);
      }),
    ),
  );
  //TODO: awatary
}
