import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';

@Component({
  selector: 'app-pick-magazine',
  templateUrl: './pick-magazine.component.html',
  styleUrls: ['./pick-magazine.component.css']
})
export class PickMagazineComponent implements OnInit {
  private magazines: any = [];
  private magazine:any={};
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private repositoryService: RepositoryService) { 
    let x = repositoryService.startProcess("posting_process");

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

    this.repositoryService.getMagazines().subscribe((data: any) => {
      this.magazines = data;
      this.magazine=data[0];
    
    });
  }

  picked(){
    this.repositoryService.pickMagazine(this.magazine.issn,this.formFieldsDto.taskId, localStorage.getItem("author")).subscribe(
      (data: any) => {
     // this.magazines = data;
     console.log("Successful picking!");
     console.log(data);
     window.location.href = '/new-article/'+this.magazine.issn;
    },
    err => {
      alert("Error with picking!");
      console.log("Error occured");
    //  window.location.href = '/registrate';
    });
      
  }

}
