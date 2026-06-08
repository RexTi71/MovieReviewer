import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieReviewPage } from './movie-review-page';

describe('MovieReviewPage', () => {
  let component: MovieReviewPage;
  let fixture: ComponentFixture<MovieReviewPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieReviewPage],
    }).compileComponents();

    fixture = TestBed.createComponent(MovieReviewPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
