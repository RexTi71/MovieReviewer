import { Component } from '@angular/core';
import {MovieEntry} from '../movie-entry/movie-entry';

@Component({
  selector: 'app-main-container',
  imports: [
    MovieEntry
  ],
  templateUrl: './main-container.html',
  styleUrl: './main-container.css',
})
export class MainContainer {
  onMovieEntryClick(movieId: number){
      console.log('Kliknięto w id: ', movieId)
  }
}
