import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RepositoryService } from '../services/repository/repository.service';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {

  id=undefined;
  constructor(private activatedRoute: ActivatedRoute,private router: Router, private repositoryService : RepositoryService) { 
  }
  ngOnInit(  ) {

    this.activatedRoute.params.subscribe(params => {
      this.id = params['id'];
    }); 
  }

  confirm(){
    this.repositoryService.registrationConfirm(this.id).subscribe(res=>{

    })
  }

}
