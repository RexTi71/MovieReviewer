import { Component, inject, OnInit } from '@angular/core';

import {NavItem} from '../nav-item/nav-item';
import {Searchbar} from '../searchbar/searchbar';
import {MovieEntry} from '../movie-entry/movie-entry';
import {RouterOutlet} from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home-page',
  imports: [NavItem, Searchbar, MovieEntry, RouterOutlet],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage implements OnInit {
  private readonly API_URL = "localhost:8080/api/v1/movies";
  private http = inject(HttpClient);

  ngOnInit() {
    this.http.get(this.API_URL).subscribe((data) =>
    {
      console.log(data);
    });
  }
}
