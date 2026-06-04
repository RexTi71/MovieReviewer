import { Component, signal } from '@angular/core';
import {RouterModule} from '@angular/router';
import {NavItem} from './component/nav-item/nav-item';

import {Searchbar} from './component/searchbar/searchbar';
import { CategoryMenu } from './component/category-menu/category-menu';

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
