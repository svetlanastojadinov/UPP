import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-preview-list-chief',
  templateUrl: './preview-list-chief.component.html',
  styleUrls: ['./preview-list-chief.component.css']
})
export class PreviewListChiefComponent implements OnInit {
  private articles=[];

  constructor(private repositoryService: RepositoryService) { 
    console.log("ulogovan "+localStorage.getItem('author'));
    
    let x = repositoryService.getArticleForChiefEditor(localStorage.getItem('author'));
    x.subscribe(
      res => {
        console.log(res);
        this.articles=res;
      },
      err=>{
        console.log(err);
      })
  }

  ngOnInit() {
  }

  view(f){
    console.log(f);
    window.location.href = '/aprove-article/'+f.id;
  }

}
