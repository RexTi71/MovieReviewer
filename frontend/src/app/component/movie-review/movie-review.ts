import { Component, input, signal } from '@angular/core';


@Component({
  selector: 'app-movie-review',
  imports: [],
  templateUrl: './movie-review.html',
  styleUrl: './movie-review.css',
})
export class MovieReview {
  title = input('Tytuł');
  rating = input('3');
  username = input('Nazwa użytkownika');
  content = input('zawartość recenzji');
  imageUrl = input('def-avatar.png');

}
