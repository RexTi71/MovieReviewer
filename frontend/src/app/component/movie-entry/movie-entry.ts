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
  title = input<String>("Tytuł")
  reviews = input<String>("3/5")
  relaseDate = input<String>("2000r.")
  imageUrl = input<String>("rem.png")

}
