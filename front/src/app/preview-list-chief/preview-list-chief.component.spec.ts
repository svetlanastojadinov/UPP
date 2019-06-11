import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewListChiefComponent } from './preview-list-chief.component';

describe('PreviewListChiefComponent', () => {
  let component: PreviewListChiefComponent;
  let fixture: ComponentFixture<PreviewListChiefComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewListChiefComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewListChiefComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
