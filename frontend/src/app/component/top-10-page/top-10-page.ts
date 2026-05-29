import { Component } from '@angular/core';
import { Top10List } from '../top-10-list/top-10-list';

@Component({
  selector: 'app-top-10-page',
  imports: [Top10List],
  templateUrl: './top-10-page.html',
  styleUrl: './top-10-page.css',
})
export class Top10Page {}
