import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AproveArticleComponent } from './aprove-article.component';

describe('AproveArticleComponent', () => {
  let component: AproveArticleComponent;
  let fixture: ComponentFixture<AproveArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AproveArticleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AproveArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
