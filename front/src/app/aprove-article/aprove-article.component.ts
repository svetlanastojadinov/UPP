import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-aprove-article',
  templateUrl: './aprove-article.component.html',
  styleUrls: ['./aprove-article.component.css']
})
export class AproveArticleComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private des="";
  private id=undefined;
  private article:any={};

  constructor(private repositoryService: RepositoryService,private activatedRoute: ActivatedRoute) { 
    let x = repositoryService.getTask("posting_process","Reviewing_new_article");

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
      this.id = params['id'];
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
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log("**** "+this.id);
  
    this.repositoryService.aproveArticle(o,this.formFieldsDto.taskId,this.id).subscribe(
      res => {
        console.log(res);
    },
    err=>{
      alert('Faild to sent article. Please, try again later.');
     
    });
  }
  

}
