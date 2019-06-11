import { Injectable } from '@angular/core';

import { RouterModule, Routes, Router } from '@angular/router';

import { Headers, RequestOptions } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  login(username, role){

    localStorage.setItem('user', username);
    localStorage.setItem('role', role);
   /*   
    let x = this.httpClient.post('http://localhost:8080/login', dto) as Observable<any>

    x.subscribe(
      res => {
        console.log(res);
          
        localStorage.setItem('user', JSON.stringify(res));
        localStorage.setItem('role', res.role);
        window.location.reload();
        this.router.navigateByUrl('/repository');
        alert("Success!");
      },
      err => {
        console.log("Error occured");
        alert("Error occured!");
      }
    );*/
  }

  dummy(to){
    let x = this.httpClient.get('http://localhost:8080/welcome/get') as Observable<any>

    x.subscribe(
      res => {
        console.log(res);
          
        
        alert("POGODJENNNNNNNNNNNNNNNNN!");
      },
      err => {
        console.log("Error occured");
        alert("Error occured!");
      }
    );
  }

  logout(){
    let x = this.httpClient.post('http://localhost:8080/logout', {}) as Observable<any>

    x.subscribe(
      res => {
        console.log(res);
        window.location.reload();
        this.router.navigateByUrl('/login');
      },
      err => {
        console.log("Error occured");
      }
    );
  }
    
}
