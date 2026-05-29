import { Routes } from '@angular/router';
import {HomePage} from './component/home-page/home-page';
import {MovieReviewPage} from './component/movie-review-page/movie-review-page';
import {LoginPage} from './component/login-page/login-page';
import {RegistrationPage} from './component/registration-page/registration-page';
import { Top10Page } from './component/top-10-page/top-10-page';

export const routes: Routes = [
  {
    path: '',
    component: HomePage,
    title: 'Strona główna',
  },
  {
    path: 'szukaj/:query',
    component: HomePage,
    title: 'Strona główna',
  },
  {
    path: 'login',
    component: LoginPage,
    title: 'Zaloguj się',
  },
  {
    path: 'rejestracja',
    component: RegistrationPage,
    title: 'Zarejestruj się',
  },
  {
    path: 'film/:id',
    component: MovieReviewPage,
    title: 'Recenzje filmu ',
  },
  {
    path: 'top10',
    component: Top10Page,
    title: 'Top 10 filmów',
  },
];
