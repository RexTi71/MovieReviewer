import { Component, inject } from '@angular/core';
import { Top10List } from '../top-10-list/top-10-list';
import { Movie } from '../home-page/home-page';
import { toSignal } from '@angular/core/rxjs-interop';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-top-10-page',
  imports: [Top10List],
  templateUrl: './top-10-page.html',
  styleUrl: './top-10-page.css',
})
export class Top10Page {
  private http = inject(HttpClient);

  movies = toSignal(
    this.http.get<Movie[]>('http://localhost:8080/api/v1/top10'),
    {initialValue: []}
  )
}
