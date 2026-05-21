import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NavItem} from './component/nav-item/nav-item';
import {MainContainer} from './component/main-container/main-container';
import {Searchbar} from './component/searchbar/searchbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavItem, MainContainer, Searchbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
  protected readonly PluginArray = PluginArray;

  onNavItemClick(tabName : string){
    console.log('Kliknięto w ', tabName);
  }
}
