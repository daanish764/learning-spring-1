import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  // this is the behaviour subject that holds and broadcasts login state
  private isAuthenticated = new BehaviorSubject<boolean>(false);

    // Observable for other components to subscribe to
  isAuthenticated$ = this.isAuthenticated.asObservable();

  constructor(private http :HttpClient, private router : Router) { }


  
  login(username : string, password :string) {


   const url = 'http://localhost:8080/auth/login';
   const body = {
    username: username,
    password: password
   }

   const headers = {
    Authorization: "Basic " + btoa(`${username}:${password}`),
    'Content-Type': "application/json"
   }


    //console.log("onSubmit was called with username="+ this.username + " password=" + this.password + " .");
    // console.log(url, body, {headers:headers, withCredentials:true});
    return this.http.post(url, body, {
      headers: headers,
      withCredentials: true, // Include cookies/session
    });
  }

  setSessionId(sessionId : string) {

    localStorage.setItem("sessionId", sessionId);
    this.isAuthenticated.next(true);
  }

  logout() {

    localStorage.removeItem("sessionId");
    this.isAuthenticated.next(false);

    const url = 'http://localhost:8080/auth/logout';

    this.http.get(url,{withCredentials:true}).subscribe({
      next: () => {this.router.navigate(['/login']);}, 
      error: err => {console.log(err, 'something went wrong!')}
    });

    
  }

  checkStatus() {
    const sessionId = localStorage.getItem("sessionId");
    if(sessionId !== null) {
      this.isAuthenticated.next(true);
    }
    else {
      this.isAuthenticated.next(false);
    }
    
  }
}
