import { Component, OnInit, ViewChild } from '@angular/core';
import { RepositoryService } from '../services/repository/repository.service';
import { UserService } from '../services/users/user.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  constructor(private userService : UserService) { }

  ngOnInit() {
  }
  login(f: NgForm){
  
    console.log(f.value);
    let x = this.userService.login(f.value);

    x.subscribe(
      res => {
        console.log(res);
        localStorage.setItem("author",f.value.username);
        localStorage.setItem("role",res.role);
        window.location.href = '/pick-magazine';
      },
      err=>{
        console.log(err);
        alert("Bad credentials");
        window.location.href = '/login';
      })
  }

}
