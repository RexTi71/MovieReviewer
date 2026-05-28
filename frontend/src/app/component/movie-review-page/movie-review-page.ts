import { Component, input } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {MovieReview} from '../movie-review/movie-review';
import { ReviewComment } from '../review-comment/review-comment';

@Component({
  selector: 'app-movie-review-page',
  imports: [NgOptimizedImage, MovieReview, ReviewComment],
  templateUrl: './movie-review-page.html',
  styleUrl: './movie-review-page.css',
})
export class MovieReviewPage {
  //TODO: awatary
  title = input("Tytuł")
  description = input("Krótki opis filmu")
  releaseDate = input(2000)
}
