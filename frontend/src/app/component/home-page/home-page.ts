import { Component } from '@angular/core';

import {NavItem} from '../nav-item/nav-item';
import {Searchbar} from '../searchbar/searchbar';
import {MovieEntry} from '../movie-entry/movie-entry';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-home-page',
  imports: [
    NavItem,
    Searchbar,
    MovieEntry,
    RouterOutlet
  ],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {

}
