import { Component, computed, inject, OnInit, signal } from '@angular/core';
import {RouterModule} from '@angular/router';
import {NavItem} from './navbar/nav-item/nav-item';

import {Searchbar} from './navbar/searchbar/searchbar';
import { CategoryMenu } from './navbar/category-menu/category-menu';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { map, of } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterModule, NavItem, Searchbar, CategoryMenu],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App{
  protected readonly title = signal('frontend');
  protected readonly PluginArray = PluginArray;

  protected readonly sessionStorage = sessionStorage;
}
