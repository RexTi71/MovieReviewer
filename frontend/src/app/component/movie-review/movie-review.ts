import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-movie-review',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './movie-review.html',
  styleUrl: './movie-review.css',
})
export class MovieReview {}
