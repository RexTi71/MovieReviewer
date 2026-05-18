import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NavItem} from './component/nav-item/nav-item';
import {MainContainer} from './component/main-container/main-container';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavItem, MainContainer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
  protected readonly PluginArray = PluginArray;
}
