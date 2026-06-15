import { Component, inject, OnInit, signal } from '@angular/core';

import {MovieEntry} from '../movie-entry/movie-entry';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { Movie } from '../../interface/movie';
import { PageSelect } from '../page-select/page-select';

@Component({
  selector: 'app-home-page',
  imports: [MovieEntry, DatePipe, PageSelect],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage implements OnInit {
  activeRoute = inject(ActivatedRoute);
  private http = inject(HttpClient);

  moviesAmount = signal<number[]>([]);

  private pageNumber = this.activeRoute.snapshot.queryParamMap.get('page');

  movies = toSignal(
    this.activeRoute.queryParamMap.pipe(
      switchMap((queryParams) => {

        const page = queryParams.get('page') ?? '0';

        return this.activeRoute.paramMap.pipe(
          switchMap((params) => {
            let url = `http://localhost:8080/api/v1/movies?page=${page}`;
            const query = params.get('query');
            const catName = params.get('name');

            if (query) {
              url = `http://localhost:8080/api/v1/search/${query}`;
            } else if (catName) {
              url = `http://localhost:8080/api/v1/category/${catName}`;
            }

            return this.http.get<Movie[]>(url);
          }),
        );
      }),
    ),
    { initialValue: [] },
  );
  ngOnInit() {
    this.http
      .get('http://localhost:8080/api/v1/movies-amount', { responseType: 'text' })
      .subscribe((res) => {
        let amount = Number(res);
        this.moviesAmount.set(Array.from({ length: amount }, (_, i) => i + 1));
      });
  }

  protected readonly String = String;
}
