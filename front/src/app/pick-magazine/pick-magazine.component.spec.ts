import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PickMagazineComponent } from './pick-magazine.component';

describe('PickMagazineComponent', () => {
  let component: PickMagazineComponent;
  let fixture: ComponentFixture<PickMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PickMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PickMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
