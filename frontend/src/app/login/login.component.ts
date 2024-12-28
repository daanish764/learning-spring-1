import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { isValid } from '../global';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string = 'admin'
  password: string = 'admin'
  error: string|null = null


  constructor(private http: HttpClient,
    private router: Router,
    private authService : AuthService) {

  }

  ngOnInit() {
    //
    this.authService.isAuthenticated$.subscribe(isLoggedIn => {
      if(isLoggedIn) {
        console.log(isLoggedIn)
        //this.router.navigate(['accounts'])
      }
    });
  }

  onSubmit() {

    var sessionId = null

    this.authService.login(this.username, this.password).subscribe({
      next: (response : any) => {
        console.log(response);
        sessionId = response.sessionId
        if(isValid(sessionId)) {
          this.authService.setSessionId(sessionId);
          this.authService.setAuthValue(this.username, this.password);
          this.router.navigate(['accounts']);
        }
        else if(isValid(response)) {
          this.authService.setTokenId(response);
          this.authService.setAuthValue(this.username, this.password);
          this.router.navigate(['accounts']);
        }
        else {
          this.error = "Login Denied!"
        }
      },
      error: (err) => { 
        console.log(err);
        this.authService.logout();
        this.error = "Login Denied!" 
      }
    });

    
  }


}
