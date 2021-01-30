import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltreBedComponent } from './filtre-bed.component';

describe('FiltreBedComponent', () => {
  let component: FiltreBedComponent;
  let fixture: ComponentFixture<FiltreBedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltreBedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltreBedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
