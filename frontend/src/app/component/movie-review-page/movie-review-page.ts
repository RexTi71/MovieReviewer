import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {MovieReview} from '../movie-review/movie-review';

@Component({
  selector: 'app-movie-review-page',
  imports: [
    NgOptimizedImage,
    MovieReview
  ],
  templateUrl: './movie-review-page.html',
  styleUrl: './movie-review-page.css',
})
export class MovieReviewPage {}
