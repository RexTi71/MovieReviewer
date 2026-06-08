import { Component, signal } from '@angular/core';
import {RouterModule} from '@angular/router';
import {NavItem} from './navbar/nav-item/nav-item';

import {Searchbar} from './navbar/searchbar/searchbar';
import { CategoryMenu } from './navbar/category-menu/category-menu';

@Component({
  selector: 'app-root',
  imports: [RouterModule, NavItem, Searchbar, CategoryMenu],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('frontend');
  protected readonly PluginArray = PluginArray;

  protected readonly sessionStorage = sessionStorage;
}
