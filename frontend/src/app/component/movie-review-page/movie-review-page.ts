import { Component, inject} from '@angular/core';
import { DatePipe, NgOptimizedImage} from '@angular/common';
import {MovieReview} from '../movie-review/movie-review';
import { toSignal } from '@angular/core/rxjs-interop';
import { Movie } from '../home-page/home-page';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { ReviewAdd } from '../review-add/review-add';
type Review = {
  movieId: string;
  title: string;
  content: string;
  rating: string;
  userId: string;
  username: string;
}
@Component({
  selector: 'app-movie-review-page',
  imports: [NgOptimizedImage, MovieReview,  DatePipe, ReviewAdd],
  templateUrl: './movie-review-page.html',
  styleUrl: './movie-review-page.css',
})
export class MovieReviewPage {
  token:string | null = sessionStorage.getItem('token');
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  id:string | null = this.route.snapshot.paramMap.get('id');
  movie = toSignal(
        this.http.get<Movie>(`http://localhost:8080/api/v1/movie/${this.id}`)
  );
  reviews = toSignal(
    this.http.get<Review[]>(`http://localhost:8080/api/v1/reviews/${this.id}`)
  );

  onClickScrollTo(el: HTMLElement){
    el.scrollIntoView();
  }
  //TODO: awatary

}
