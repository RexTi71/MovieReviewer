import { Routes } from '@angular/router';
import {HomePage} from './home/home-page/home-page';
import {MovieReviewPage} from './movie-details/movie-review-page/movie-review-page';
import {LoginPage} from './profile/login-page/login-page';
import {RegistrationPage} from './profile/registration-page/registration-page';
import { Top10Page } from './top-10/top-10-page/top-10-page';
import { ProfilePage } from './profile/profile-page/profile-page';
import { AdminPage } from './admin/admin-page/admin-page';

export const routes: Routes = [
  {
    path: '',
    component: HomePage,
    title: 'Strona główna',
  },
  {
    path: 'kategoria/:name',
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
    path: 'admin',
    component: AdminPage,
    title: 'Panel administratora',
  },
  {
    path: 'profil',
    component: ProfilePage,
    title: 'Profil',
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
