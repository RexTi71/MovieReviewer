import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieReview } from './movie-review';

describe('MovieReview', () => {
  let component: MovieReview;
  let fixture: ComponentFixture<MovieReview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieReview],
    }).compileComponents();

    fixture = TestBed.createComponent(MovieReview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
