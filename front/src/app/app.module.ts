import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { RepositoryComponent } from './repository/repository.component';
import { UsersComponent } from './users/users.component';


import { AuthenticationService } from './services/authentication/authentication.service';
import { ProfileChangeService } from './services/profile_change/profile-change.service';
import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/users/user.service';

import { RegistrationComponent } from './registration/registration.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { RegistrationConfirmComponent } from './registration-confirm/registration-confirm.component';
import { LoginComponent } from './login/login.component';
import { PickMagazineComponent } from './pick-magazine/pick-magazine.component';
import { NewArticleComponent } from './new-article/new-article.component';
import { AproveArticleComponent } from './aprove-article/aprove-article.component';
import { PreviewListChiefComponent } from './preview-list-chief/preview-list-chief.component';
import { EditArticleAuthorComponent } from './edit-article-author/edit-article-author.component';
import { ListRecezComponent } from './list-recez/list-recez.component';
import { CommentArticleComponent } from './comment-article/comment-article.component';
import { HomeComponent } from './home/home.component';

const ChildRoutes =
  [
  ]

  const RepositoryChildRoutes =
  [
    
  ]

const Routes = [
  
  
  {
    path: "repository",
    component: RepositoryComponent,
    children: RepositoryChildRoutes
  },
  {
    path: "users",
    component: UsersComponent,
    canActivate: [Admin]
  },
  {
    path: "registrate",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate:[Notauthorized]
  },{
    path: "pick-magazine",
    component: PickMagazineComponent,
    canActivate:[Notauthorized]
  },
  {
    path: "new-article/:id",
    component: NewArticleComponent,
    canActivate:[Notauthorized]
  },
  {
    path: "aprove-article/:id",
    component: AproveArticleComponent,
    canActivate:[Notauthorized]
  },
  {
    path: "preview-list-chief",
    component: PreviewListChiefComponent,
    canActivate:[Notauthorized]
  },
  {
    path: "home",
    component: HomeComponent,
    canActivate:[Notauthorized]
  },{
    path: "list-recez",
    component: ListRecezComponent,
    canActivate:[Notauthorized]
  },
  {
    path: "registration/confirm/:id",
    component: RegistrationConfirmComponent,
    canActivate:[Notauthorized]
  },
  {
    path:"comment-article",
    component: CommentArticleComponent,
    canActivate:[Notauthorized]
  }
]

@NgModule({
  declarations: [
    AppComponent,
    RepositoryComponent,
    UsersComponent,
    RegistrationComponent,
    RegistrationConfirmComponent,
    LoginComponent,
    PickMagazineComponent,
    NewArticleComponent,
    AproveArticleComponent,
    PreviewListChiefComponent,
    EditArticleAuthorComponent,
    ListRecezComponent,
    CommentArticleComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule, 
    HttpModule
  ],
  
  providers:  [
    Admin,
    Authorized,
    Notauthorized
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
