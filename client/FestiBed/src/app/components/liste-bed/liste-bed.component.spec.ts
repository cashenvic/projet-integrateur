import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeBedComponent } from './liste-bed.component';

describe('ListeBedComponent', () => {
  let component: ListeBedComponent;
  let fixture: ComponentFixture<ListeBedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListeBedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeBedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
