import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListRecezComponent } from './list-recez.component';

describe('ListRecezComponent', () => {
  let component: ListRecezComponent;
  let fixture: ComponentFixture<ListRecezComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListRecezComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListRecezComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
