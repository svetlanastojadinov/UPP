import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { FormGroup, FormBuilder, FormControl, FormArray , ReactiveFormsModule, FormsModule} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-recez',
  templateUrl: './list-recez.component.html',
  styleUrls: ['./list-recez.component.css']
})
export class ListRecezComponent implements OnInit {

  rec : any;
  private izabrani=[];
  private flag=false;
  private magazine;
  private issn:string;
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private id=undefined;
  private article={};
  constructor(private repositoryService :RepositoryService,private activatedRoute: ActivatedRoute) {  
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
    this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
      console.log("article id "+this.id);

      this.repositoryService.getOneMagazineByArticle(this.id).subscribe(
        res=>{
          this.magazine=res.issn;
          console.log("***** "+this.magazine);

          this.rec=this.repositoryService.getRecezenti(this.magazine).subscribe(
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
          })  
        }
      )

    
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
        window.location.href = '/home';
      },
      err=>{
        console.log(err);
      })
  }

}
