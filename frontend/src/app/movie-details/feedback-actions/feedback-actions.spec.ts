import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackActions } from './feedback-actions';

describe('FeedbackActions', () => {
  let component: FeedbackActions;
  let fixture: ComponentFixture<FeedbackActions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeedbackActions],
    }).compileComponents();

    fixture = TestBed.createComponent(FeedbackActions);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
