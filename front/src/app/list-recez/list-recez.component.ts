import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { FormGroup, FormBuilder, FormControl, FormArray , ReactiveFormsModule, FormsModule} from '@angular/forms';

@Component({
  selector: 'app-list-recez',
  templateUrl: './list-recez.component.html',
  styleUrls: ['./list-recez.component.css']
})
export class ListRecezComponent implements OnInit {

  rec : any;
  private izabrani=[];
  private flag=false;
  private issn="93545180";
  private id="17";
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  constructor(private repositoryService :RepositoryService) {  
    let x = repositoryService.getTask("posting_process","Choosing_reviewers");

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
    this.rec=this.repositoryService.getRecezenti(this.issn).subscribe(
      res => {
        console.log(res);
        this.rec=res;
        if(this.rec.length==0){
          this.flag=true;
        }
      })

    for(var i=0;i<this.rec.length;i++){
      this.rec[i].checked=false;
    }
   
  }
  get selectedOptions() { // right now: ['1','3']
  return this.rec
            .filter(opt => opt.checked)
            .map(opt => opt.name)
}
  submit(){
    console.log(this.selectedOptions);
    var rec=this.selectedOptions;
    if(this.selectedOptions.length==0){
      console.log("******");
      rec=localStorage.getItem('author');
      console.log("rec "+rec);
    }
    let x = this.repositoryService.setRecezenti(this.formFieldsDto.taskId,this.id,rec);
    x.subscribe(
      res => {
        console.log(res);
      },
      err=>{
        console.log(err);
      })
    console.log(this.selectedOptions);
  }

}
