import { Component, signal } from '@angular/core';
import {RouterModule} from '@angular/router';
import {NavItem} from './component/nav-item/nav-item';

import {Searchbar} from './component/searchbar/searchbar';

@Component({
  selector: 'app-root',
  imports: [RouterModule, NavItem, Searchbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
  protected readonly PluginArray = PluginArray;
  onNavItemClick(tabName : string){
    console.log('Kliknięto w ', tabName);
  }

  protected readonly sessionStorage = sessionStorage;
}
