import { Component, inject, signal } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { toSignal } from '@angular/core/rxjs-interop';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
type Account = {
  username: string;
}
type Review = {
  token: string | null;
  movieId: string | null;
  rating: string | null | undefined;
  title: string | null | undefined;
  content: string | null | undefined;
};
@Component({
  selector: 'app-review-add',
  imports: [NgOptimizedImage, FormsModule, ReactiveFormsModule],
  templateUrl: './review-add.html',
  styleUrl: './review-add.css',
})
export class ReviewAdd {
  token: string | null = sessionStorage.getItem('token');
  private http = inject(HttpClient);
  private activeRoute = inject(ActivatedRoute);
  private route = inject(Router);
  private id = this.activeRoute.snapshot.paramMap.get('id');

  addReviewForm = new FormGroup({
    title: new FormControl(''),
    rating: new FormControl(''),
    content: new FormControl(''),
  });
  account = toSignal(
    this.http.get<Account>(`http://localhost:8080/api/auth/me?token=${this.token}`)
  );
  avatar = signal('rem.png');
  trescRecenzji: string = '';
  onReviewSubmit(){

    const review: Review = {
      token: sessionStorage.getItem('token'),
      movieId: this.id,
      rating: this.addReviewForm.value.rating,
      title: this.addReviewForm.value.title,
      content: this.addReviewForm.value.content,
    };
    this.http.post('http://localhost:8080/api/v1/review', review).subscribe((res) =>{
      console.log(res)
      this.route.navigate(['/film/',this.id])});
  }
}
