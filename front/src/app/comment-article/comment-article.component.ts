import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute } from '@angular/router';
import { StringifyOptions } from 'querystring';

@Component({
  selector: 'app-comment-article',
  templateUrl: './comment-article.component.html',
  styleUrls: ['./comment-article.component.css']
})
export class CommentArticleComponent implements OnInit { private repeated_password = "";
private categories = [];
private formFieldsDto = null;
private formFields = [];
private choosen_category = -1;
private processInstance = "";
private enumValues = [];
private tasks = [];
private id="17";
private article:any={};
user=localStorage.getItem('author');
private comment:string;
private commentForEditor:string;
private des=["Article is irelevant","Article should be edited","Article is accepted"];
private desicion:string; 

constructor(private repositoryService: RepositoryService,private activatedRoute: ActivatedRoute) { 
  let x = repositoryService.getTaskByA("posting_process","Entering comments",this.user);

x.subscribe(
  res => {
    console.log(res);
    //this.categories = res;
    this.formFieldsDto = res;
    this.formFields = res.formFields;
    this.processInstance = res.processInstanceId;
    this.formFields.forEach( (field) =>{
      
      if( field.type.name=='enum'){
        this.enumValues = Object.keys(field.type.values);
      }
    });
  },
  err => {
    console.log("Error occured");
  }
);
}

ngOnInit() {
  this.activatedRoute.params.subscribe(params => {
    this.repositoryService.getOneArticle(this.id).subscribe(
      res=>{
        console.log(res);
        this.article.title=res.title;
        this.article.abst=res.abst;
        this.article.keyWords=res.keyWords;
      }
    );
  }); 
}

onSubmit(value, form){
  console.log("**** "+this.comment);
  console.log(" "+this.desicion);
  console.log("--- "+this.commentForEditor);

  this.repositoryService.aproveArticleRecezent(this.formFieldsDto.taskId,this.desicion,this.comment,this.commentForEditor).subscribe(
    res => {
      console.log(res);
  },
  err=>{
    alert('Faild to sent article. Please, try again later.');
   
  });
}


}
