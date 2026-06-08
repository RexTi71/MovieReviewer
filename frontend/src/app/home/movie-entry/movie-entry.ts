import { Component, input } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-movie-entry',
  imports: [RouterLink],
  templateUrl: './movie-entry.html',
  styleUrl: './movie-entry.css',
})
export class MovieEntry {
  id = input<number>(1);
  title = input<string>('Tytuł');
  reviews = input<string | null>('-/5');
  productionDate = input<string | null>('2000r.');
  imageUrl = input<string>('rem.png');

}
