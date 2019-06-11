import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditArticleAuthorComponent } from './edit-article-author.component';

describe('EditArticleAuthorComponent', () => {
  let component: EditArticleAuthorComponent;
  let fixture: ComponentFixture<EditArticleAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditArticleAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditArticleAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
