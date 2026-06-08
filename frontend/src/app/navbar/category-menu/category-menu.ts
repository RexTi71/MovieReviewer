import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { Category } from '../../interface/category';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-menu',
  imports: [],
  templateUrl: './category-menu.html',
  styleUrl: './category-menu.css',
})
export class CategoryMenu {
  private http = inject(HttpClient);
  private router = inject(Router);
  categories = toSignal(
    this.http.get<Category[]>('http://localhost:8080/api/v1/categories'),
    {initialValue: []}
  )
  onCategoryClick(category:string){
    this.router.navigate(['kategoria/',category]);
  }
}
