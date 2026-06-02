import { Component, input } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-top-10-list',
  imports: [NgOptimizedImage, RouterModule],
  templateUrl: './top-10-list.html',
  styleUrl: './top-10-list.css',
})
export class Top10List {
  position = input(1);
  id = input<number>(1);
  rating = input('1');
  title = input('Tytuł');
  description = input<string>(
    'Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n' +
      "      Lorem Ipsum has been the industry's standard dummy text ever since 1966, when designers at Letraset and James Mosley, the librarian at St Bride Printing Library, took a 1914 Cicero translation and scrambled it to make dummy text for Letraset's Body Type sheets.\n" +
      '      It has survived not only many decades, but also the leap into electronic typesetting, remaining essentially unchanged.\n' +
      '      It was popularised thanks to these sheets and more recently with desktop publishing software including versions of Lorem Ipsum.',
  ); //nie mowimy o tym
  imageUrl = input<string>('michael.jpg');
}
