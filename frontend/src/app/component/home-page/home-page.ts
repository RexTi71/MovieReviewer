import { Component, inject } from '@angular/core';

import {MovieEntry} from '../movie-entry/movie-entry';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { DatePipe } from '@angular/common';

export type Movie = {
  id: number;
  title: string;
  description: string;
  productionDate: string;
};

@Component({
  selector: 'app-home-page',
  imports: [MovieEntry, DatePipe],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
  private http = inject(HttpClient);
  movies = toSignal(this.http.get<Movie[]>('http://localhost:8080/api/v1/movies'), {
    initialValue: [],
  });
}
