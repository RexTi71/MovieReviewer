import { Component, inject, input, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import {CommentAdd} from '../../interface/comment-add';


@Component({
  selector: 'app-movie-review',
  imports: [ReactiveFormsModule],
  templateUrl: './movie-review.html',
  styleUrl: './movie-review.css',
})
export class MovieReview {
  private http = inject(HttpClient);
  private activeRoute = inject(ActivatedRoute);
  private token = sessionStorage.getItem('token');

  title = input('Tytuł');
  rating = input('3');
  username = input('Nazwa użytkownika');
  content = input('zawartość recenzji');
  imageUrl = input('def-avatar.png');

  commentAddForm = new FormGroup({
    content: new FormControl(''),
  });


  onSubmit(){
    const comment: CommentAdd = {
      content: this.commentAddForm.get('content')?.value,
      token: this.token,
      movieId:this.activeRoute.snapshot.paramMap.get('id'),
    };
    this.http.post(`http://localhost:8080/api/v1/comment`,comment).subscribe({
      next : (res)=> {
        console.log(res)
      },
      error: (err) => {
        console.log(err.error)
      },
      })
  }
}
