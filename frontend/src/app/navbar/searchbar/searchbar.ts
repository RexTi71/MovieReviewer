import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-searchbar',
  imports: [ReactiveFormsModule],
  templateUrl: './searchbar.html',
  styleUrl: './searchbar.css',
})
export class Searchbar {
  fb = new FormBuilder();
  private http = inject(HttpClient);
  private route = inject(Router);
  searchForm = this.fb.group({
    searchQuery: ['' /*,[Validators.maxLength(255)]*/],
  });

  onSubmit() {
    const query = this.searchForm.get("searchQuery")?.value;
    this.route.navigate([`szukaj/`, query]);
    //Usunac pozniej
    // this.http.get(`http://localhost:8080/api/v1/search/${query}`)
    console.log(this.searchForm.get("searchQuery")?.errors);
    console.log(this.searchForm.get("searchQuery")?.value)

  }
}
