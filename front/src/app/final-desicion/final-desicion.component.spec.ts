import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalDesicionComponent } from './final-desicion.component';

describe('FinalDesicionComponent', () => {
  let component: FinalDesicionComponent;
  let fixture: ComponentFixture<FinalDesicionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalDesicionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalDesicionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
