import {Component, input} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-movie-entry',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './movie-entry.html',
  styleUrl: './movie-entry.css',
})
export class MovieEntry {
  title = input<String>("tytuł")
  user = input<String>("użytkownik")
  description = input<String>("opis")
  imageUrl = input<String>("rem.png")

}
