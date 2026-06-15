import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageSelect } from './page-select';

describe('PageSelect', () => {
  let component: PageSelect;
  let fixture: ComponentFixture<PageSelect>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PageSelect],
    }).compileComponents();

    fixture = TestBed.createComponent(PageSelect);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
