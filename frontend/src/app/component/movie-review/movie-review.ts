import { Component, input } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-movie-review',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './movie-review.html',
  styleUrl: './movie-review.css',
})
export class MovieReview {
  //TODO: awatary
  title = input("Tytuł")
  rating = input("3/5")
  username = input("Nazwa użytkownika")
  content = input("zawartość recenzji")

}
