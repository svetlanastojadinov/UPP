import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.css']
})
export class NewArticleComponent implements OnInit {

  private magazines: any = [];
  private magazine:any={};
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  fileToUpload: File = null;
  mes: String;
  private scientificAreas: any = [];
  private id=undefined;
  private sc:any={};

  constructor(private repositoryService: RepositoryService,private activatedRoute: ActivatedRoute) { 
    let x = repositoryService.getTask("posting_process","Entering_data");

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
  })
  this.repositoryService.getScientificAreas().subscribe((data: any) => {
    console.log(data);
    this.scientificAreas = data;
  });
 } 

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
  
    this.repositoryService.uploadMagazine(o,this.formFieldsDto.taskId,this.id,this.fileToUpload).subscribe(
      res => {
     // console.log(data);
    //  alert('Article sent to Chief Editor.');
     // this.router.navigate(["/center"]);
   //  window.location.href = '/home';
    },
    err=>{o
      alert('Faild to sent article. Please, try again later.');
     
    });
  }
  
  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
 }
 
}
