import { Component, inject, signal } from '@angular/core';
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
  imports: [FormsModule, ReactiveFormsModule],
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
    this.http.get<Account>(`http://localhost:8080/api/auth/me?token=${this.token}`),
  );
  avatarUrl = signal(`http://localhost:8080/api/avatar?token=${this.token}`);
  trescRecenzji: string = '';
  onReviewSubmit() {
    const review: Review = {
      token: sessionStorage.getItem('token'),
      movieId: this.id,
      rating: this.dajGwiazdki().toString(),
      title: this.addReviewForm.value.title,
      content: this.addReviewForm.value.content,
    };
    this.http.post('http://localhost:8080/api/v1/review', review).subscribe((res) => {
      console.log(res);
      this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        this.route.navigate(['/film/', this.id], { state: { addedReview: true }});
      });
    });
  }

  tablicaGwiazdek = Array(5).fill(0)

  dajGwiazdki(){
    let g = this.tablicaGwiazdek.lastIndexOf(1)+1;
    console.log(g);
    return g;
  }
  ustawGwiazdki(num:number){
    this.tablicaGwiazdek = this.tablicaGwiazdek.map((t,i)=>{if(i<=num)return 1;return 0;});
  }
}
