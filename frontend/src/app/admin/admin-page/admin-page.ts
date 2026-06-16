import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { Movie } from '../../interface/movie';
import { ReportResponse } from '../../interface/report-response';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-page',
  imports: [ReactiveFormsModule],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.css',
})
export class AdminPage {
  private http = inject(HttpClient);
  private token = sessionStorage.getItem('token');
  private route = inject(Router);
  page = 0;

  movieForm = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    productionDate: new FormControl(''),
    categories: new FormControl('')
  });

  isAdmin = toSignal(
    this.http.get<boolean>(`http://localhost:8080/api/auth/admin?token=${this.token}`),
    {initialValue: false}
  )
  reRoute(){
    this.route.navigate(['/']);
  }
  movies = toSignal(
    this.http.get<Movie[]>(`http://localhost:8080/api/v1/movies?page=${this.page}`),
    { initialValue: [] },
  );
  reports = toSignal(this.http.get<ReportResponse[]>('http://localhost:8080/api/v1/reports'), {
    initialValue: [],
  });

  onMovieFormSubmit() {
    const body = {
      title: this.movieForm.value.title,
      description: this.movieForm.value.description,
      productionDate: this.movieForm.value.productionDate,
      categories: this.movieForm.value.categories,
    };
    this.http.post(`http://localhost:8080/api/v1/movie?token=${this.token}`, body).subscribe({
      next: res =>{
        console.log(res);

      },
      error: err => {
        console.log(err);
      }
    });
  }
  deleteMovie(id:number){
    this.http.delete(`http://localhost:8080/api/v1/movie/${id}`).subscribe({
      next: res =>{
        console.log(res);
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
