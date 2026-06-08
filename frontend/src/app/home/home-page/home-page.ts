import { Component, inject } from '@angular/core';

import {MovieEntry} from '../movie-entry/movie-entry';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs';
import { Movie } from '../../interface/movie';

@Component({
  selector: 'app-home-page',
  imports: [MovieEntry, DatePipe],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
  activeRoute = inject(ActivatedRoute);

  private http = inject(HttpClient);
  movies = toSignal(
    this.activeRoute.paramMap.pipe(
      switchMap((params) => {
        //Pobierz wszystkie filmy
        let url = 'http://localhost:8080/api/v1/movies';
        const query = params.get('query');
        const catName = params.get('name');
        //Jezeli uzytkownik szuka konkretny film
        //wyslij na zapytanie na ten adres
        if (query) {
          url = `http://localhost:8080/api/v1/search/${query}`;
        }else if(catName){
          url = `http://localhost:8080/api/v1/category/${catName}`
        }

        return this.http.get<Movie[]>(url);
      }),
    ),
    { initialValue: [] },
  );
}
