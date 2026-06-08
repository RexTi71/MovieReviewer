import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewComment } from './review-comment';

describe('ReviewComment', () => {
  let component: ReviewComment;
  let fixture: ComponentFixture<ReviewComment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReviewComment],
    }).compileComponents();

    fixture = TestBed.createComponent(ReviewComment);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
