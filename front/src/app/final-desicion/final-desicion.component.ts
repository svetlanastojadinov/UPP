import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-final-desicion',
  templateUrl: './final-desicion.component.html',
  styleUrls: ['./final-desicion.component.css']
})
export class FinalDesicionComponent implements OnInit {
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
}
}