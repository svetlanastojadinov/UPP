import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-aprove-article',
  templateUrl: './aprove-article.component.html',
  styleUrls: ['./aprove-article.component.css']
})
export class AproveArticleComponent1 implements OnInit {
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
    let x = repositoryService.getTask("posting_process","Final_desicion");

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
      console.log(this.id);
    }); 
}

onSubmit(value, form){
  let o = new Array();
  for (var property in value) {
    this.des=value[property];
    console.log(property);
    console.log(value[property]);
    o.push({fieldId : property, fieldValue : value[property]});
  }
  
  this.repositoryService.aproveArticleFinal(this.des,this.formFieldsDto.taskId,this.id).subscribe(
    res => {
      console.log(res);
      window.location.href = '/home';
  },
  err=>{
    alert('Faild to sent article. Please, try again later.');
   
  });
}
}