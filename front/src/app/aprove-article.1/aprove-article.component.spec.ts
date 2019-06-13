import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AproveArticleComponent1 } from './aprove-article.component';

describe('AproveArticleComponent', () => {
  let component: AproveArticleComponent1;
  let fixture: ComponentFixture<AproveArticleComponent1>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AproveArticleComponent1 ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AproveArticleComponent1);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
