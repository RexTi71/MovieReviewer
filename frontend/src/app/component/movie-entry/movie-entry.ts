import { Component, input } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-movie-entry',
  imports: [NgOptimizedImage, RouterLink],
  templateUrl: './movie-entry.html',
  styleUrl: './movie-entry.css',
})
export class MovieEntry {
  id = input<number>(1);
  title = input<String>('Tytuł');
  reviews = input<String>('3/5');
  relaseDate = input<String>('2000r.');
  imageUrl = input<String>('rem.png');

}
