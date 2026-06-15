import { Component, inject, input } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-page-select',
  imports: [],
  templateUrl: './page-select.html',
  styleUrl: './page-select.css',
})
export class PageSelect {

  private router = inject(Router);
  character = input("..");

  onPageSelect(){
    let realPageNumber;

    try {
      realPageNumber = String(Number(this.character()) - 1);
    }catch(e) {
      console.log(e);
      realPageNumber = 0;
    }

    this.router.navigate(['/'],{queryParams: {page: realPageNumber}})
  }
}
