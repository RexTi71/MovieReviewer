import { Component, input } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';

@Component({
  selector: 'app-review-comment',
  imports: [NgOptimizedImage],
  templateUrl: './review-comment.html',
  styleUrl: './review-comment.css',
})
export class ReviewComment {
  //TODO: awatary
  username = input("Nazwa użytkownika")
  content = input("Zawartość komentarza")
}
