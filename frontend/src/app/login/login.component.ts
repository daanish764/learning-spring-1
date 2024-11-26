import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string = 'admin'
  password: string = 'admin'


  constructor(private http: HttpClient, private router: Router) {

  }

  onSubmit() {


   const url = 'http://localhost:8080/auth/login';
   const body = {
    username: this.username,
    password: this.password
   }

   const headers = {
    Authorization: "Basic " + btoa(`${this.username}:${this.password}`),
    'Content-Type': "application/json"
   }

    //console.log("onSubmit was called with username="+ this.username + " password=" + this.password + " .");
    console.log(url, body, {headers:headers, withCredentials:true});
    this.http.post(url, body, {
      headers: headers,
      withCredentials: true, // Include cookies/session
    }).subscribe({
      next: (response) => {
        console.log(response);
        this.router.navigate(['accounts']);
      },
      error: (err) => {
        alert('LOGIN FAILED!');
        console.log(err);
      }
    });
    
  }

}
