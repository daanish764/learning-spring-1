import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { authMethod, isValid } from './global';



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private username : string | null = null
  private password : string | null = null
  
  // this is the behaviour subject that holds and broadcasts login state
  private isAuthenticated = new BehaviorSubject<boolean>(false);

    // Observable for other components to subscribe to
  isAuthenticated$ = this.isAuthenticated.asObservable();

  constructor(private http :HttpClient, private router : Router) { }



  getAuthorizationHeader() : string |null {

    // get auth header from logs
    const authHeader = localStorage.getItem('auth');
    console.log(authHeader);
    if(authHeader !== null) {
      return "Basic " + authHeader;
    }
 

    if(this.username && this.password) {
      console.log("username" , this.username);
      console.log("password", this.password);
      return "Basic " + btoa(`${this.username}:${this.password}`);
    }
    console.log("*****************")
    return null;

  }


  getTokenHeader() : string |null {

    // oauth token
    const token = localStorage.getItem('token');

    if(token !== null) {
      return "Bearer " + token;
    }

    return null;

  }


  
  login(username : string, password :string) {

    this.username = username
    this.password = password


   const url = 'http://localhost:8080/auth/login';
   const body = {
    username: username,
    password: password
   }

   const headers = {
    'Content-Type': "application/json"
   }

   
   //console.log("onSubmit was called with username="+ this.username + " password=" + this.password + " .");
    console.log(url, body, {headers:headers, withCredentials:true});
    return this.http.post<any>(url, body, {
      responseType: 'text' as any,
      headers: headers,
      withCredentials: true, // Include cookies/session
    });
  }

  setSessionId(sessionId : string) {
    localStorage.setItem("sessionId", sessionId);
    this.isAuthenticated.next(true); 
  }

  setTokenId(token : string) {
    localStorage.setItem("token", token);
    this.isAuthenticated.next(true); 
  }

  setAuthValue(username :string, password :string) {
    const hashAuthCredentialValue = btoa(`${username}:${password}`)
    localStorage.setItem('auth', hashAuthCredentialValue)
  }

  logout() {


    const url = 'http://localhost:8080/auth/logout';

    this.http.get(url,{withCredentials:true}).subscribe({
      next: () => {
        this.username = null;
        this.password = null;
        this.isAuthenticated.next(false);
        this.router.navigate(['/login']);
      }, 
      error: err => {console.log(err, 'something went wrong!')}
    });


    localStorage.removeItem("sessionId");
    localStorage.removeItem("token")

    
  }

  checkStatus() {
    let authenticated = false
    
    if(authMethod === 'TOKEN') {
      const token = localStorage.getItem("token");
      if(isValid(token)) {
        this.isAuthenticated.next(true);
      }
    }
    else if(authMethod === 'BASIC_AUTH') {
      const sessionId = localStorage.getItem("sessionId");

      if(isValid(sessionId)) {
        this.isAuthenticated.next(true);
      }
    }
    else {
      this.isAuthenticated.next(false);
    }
    
  }

  isLoggedIn() {
    return this.isAuthenticated.getValue() === true;
  }
}
