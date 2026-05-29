import { Routes } from '@angular/router';
import {HomePage} from './component/home-page/home-page';
import {MovieReviewPage} from './component/movie-review-page/movie-review-page';
import {LoginPage} from './component/login-page/login-page';
import {RegistrationPage} from './component/registration-page/registration-page';
import { ProfilePage } from './component/profile-page/profile-page';

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


];
