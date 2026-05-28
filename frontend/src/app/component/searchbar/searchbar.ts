import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-searchbar',
  imports: [ReactiveFormsModule],
  templateUrl: './searchbar.html',
  styleUrl: './searchbar.css',
})
export class Searchbar {
  fb = new FormBuilder();
  searchForm = this.fb.group({
    searchQuery: ['' /*,[Validators.maxLength(255)]*/],
  });

  onSubmit() {
    //Usunac pozniej
    console.log(this.searchForm.get("searchQuery")?.errors);
    console.log(this.searchForm.get("searchQuery")?.value)
  }
}
