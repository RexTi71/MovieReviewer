import { Routes } from '@angular/router';
import {HomePage} from './component/home-page/home-page';
import {MovieReviewPage} from './component/movie-review-page/movie-review-page';

export const routes: Routes = [
  {
    path: '',
    component: HomePage,
    title: 'Strona główna'
  },
  {
    path: ':id',
    component: MovieReviewPage,
    title: 'Recenzje filmu '
  }

];
