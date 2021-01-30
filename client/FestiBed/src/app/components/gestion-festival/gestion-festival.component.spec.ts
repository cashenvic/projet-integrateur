import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionFestivalComponent } from './gestion-festival.component';

describe('GestionFestivalComponent', () => {
  let component: GestionFestivalComponent;
  let fixture: ComponentFixture<GestionFestivalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestionFestivalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionFestivalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
