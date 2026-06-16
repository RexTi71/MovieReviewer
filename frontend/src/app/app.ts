import { Component, inject, signal } from '@angular/core';
import {RouterModule} from '@angular/router';
import {NavItem} from './navbar/nav-item/nav-item';

import {Searchbar} from './navbar/searchbar/searchbar';
import { CategoryMenu } from './navbar/category-menu/category-menu';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-root',
  imports: [RouterModule, NavItem, Searchbar, CategoryMenu],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private http = inject(HttpClient);
  private token = sessionStorage.getItem('token');

  isAdmin = toSignal(
    this.http.get(`http://localhost:8080/api/auth/admin?token=${this.token}`),
    {initialValue: false}
  )
  protected readonly title = signal('frontend');
  protected readonly PluginArray = PluginArray;

  protected readonly sessionStorage = sessionStorage;
}
