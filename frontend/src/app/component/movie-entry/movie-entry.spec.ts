import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieEntry } from './movie-entry';

describe('MovieEntry', () => {
  let component: MovieEntry;
  let fixture: ComponentFixture<MovieEntry>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieEntry],
    }).compileComponents();

    fixture = TestBed.createComponent(MovieEntry);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
