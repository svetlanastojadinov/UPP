import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders, HttpEvent, HttpRequest } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';
import { StringifyOptions } from 'querystring';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http : Http) { 
    

  }


  startProcess(process: string){
    return this.httpClient.get('http://localhost:8080/welcome/start/'.concat(process)) as Observable<any>
  }
  

  getTasks(processInstance : string){

    return this.httpClient.get('http://localhost:8080/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  }
  getTask(processName : string, taskName:string){

    return this.httpClient.get('http://localhost:8080/welcome/'+processName+'/task/'+taskName) as Observable<any>
  }
  getTaskByA(processName : string, taskName:string,username:string){

    return this.httpClient.get('http://localhost:8080/welcome/'+processName+'/task/'+taskName+'/'+username) as Observable<any>
  }

  claimTask(taskId){
    return this.httpClient.post('http://localhost:8080/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  }

  completeRegistration(task, taskId){
    return this.httpClient.post('http://localhost:8080/user/registration/'.concat(taskId), task) as Observable<any>
  }
 
  registrationConfirm(id){
    return this.httpClient.post('http://localhost:8080/user/registration/confirm/'.concat(id), null) as Observable<any>
  }

  getMagazines() {
    return this.httpClient.get("http://localhost:8080/api/magazines") as Observable<any>
  }
  getOneMagazine(id: string) {
    return this.httpClient.get("http://localhost:8080/api/magazines/".concat(id)) as Observable<any>
  }
  getOneArticle(id: string) {
    return this.httpClient.get("http://localhost:8080/api/articles/"+id) as Observable<any>
  }
  getArticleForChiefEditor(username:string){
    console.log("get Article "+username);
    return this.httpClient.get("http://localhost:8080/api/articles/chief=".concat(username)) as Observable<any>
  }
  getArticleForEdit(username:string){
    return this.httpClient.get("http://localhost:8080/api/articles/edito=".concat(username)) as Observable<any>
  }
//"/recezenti/{area}"
getRecezenti(magId:string){
  return this.httpClient.get("http://localhost:8080/api/magazines/recezenti/mag=".concat(magId)) as Observable<any>
}
setRecezenti(taskId:string,issn:string,list:any){
  return this.httpClient.post("http://localhost:8080/user/izabrani_recezenti/"+taskId+"/issn="+issn,list )as Observable<any>
}

  pickMagazine(magazine:string, taskId:string, author: string) {
    return this.httpClient.post("http://localhost:8080/api/articles/pickMagazine/".concat(magazine)+"/"+author+"/"+taskId, null) as Observable<any>
  }
  aproveArticle(o:any, taskId:string,article:string) {
    return this.httpClient.post("http://localhost:8080/api/articles/"+taskId+"/aprove="+article,o) as Observable<any>
  }
  aproveArticleRecezent(taskId:string,des:string,comment:string,commentForEditor:string) {
    return this.httpClient.post("http://localhost:8080/api/articles/task="+taskId+"/des="+des,null) as Observable<any>
  }

  getScientificAreas(){
    return this.httpClient.get("http://localhost:8080/api/scientificarea") as Observable<any>
  }
  
  uploadMagazine(o: any,taskId:string,id:string,file: File): Observable<HttpEvent<{}>>  {
    console.log("sada smo ovde "+file.name);
    
    let formdata: FormData = new FormData();
   
    formdata.append('file', file);
    var title=o[0].fieldValue.replace(' ','+');
      var key_words=o[1].fieldValue.replace(' ','+');
      var abstract=o[2].fieldValue.replace(' ','+');
      var sc=o[3].fieldValue;

      console.log("1");
      const req = new HttpRequest('POST', 'http://localhost:8080/api/articles/add='+taskId+'/id='+id+'/title='+title+'/area='+sc+'/keyWords='+key_words+'/abstract='+abstract, formdata, {
        reportProgress: true,
        responseType: 'text'
      });
      console.log("zavrsio front");
   
      return this.httpClient.request(req);
    }

}
