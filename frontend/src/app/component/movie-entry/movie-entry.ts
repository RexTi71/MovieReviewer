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
  title = input<string>('Tytuł');
  reviews = input<string>('3/5');
  productionDate = input<string | null>('2000r.');
  imageUrl = input<string>('rem.png');

}
