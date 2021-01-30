import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeFestivalsComponent } from './liste-festivals.component';

describe('ListeFestivalsComponent', () => {
  let component: ListeFestivalsComponent;
  let fixture: ComponentFixture<ListeFestivalsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListeFestivalsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeFestivalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
