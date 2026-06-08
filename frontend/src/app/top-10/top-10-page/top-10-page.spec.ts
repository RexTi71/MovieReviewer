import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Top10Page } from './top-10-page';

describe('Top10Page', () => {
  let component: Top10Page;
  let fixture: ComponentFixture<Top10Page>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Top10Page],
    }).compileComponents();

    fixture = TestBed.createComponent(Top10Page);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
