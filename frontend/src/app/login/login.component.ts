import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

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

  onSubmit() {

    var sessionId = null

    this.authService.login(this.username, this.password).subscribe({
      next: (response : any) => {
        console.log(response.sessionId);
        sessionId = response.sessionId
        if(sessionId !== null) {
          this.authService.setSessionId(sessionId);
          this.router.navigate(['accounts']);
        }
        else {
          this.error = "Login Denied!"
        }
      },
      error: () => { this.error = "Login Denied!" }
    });

    
  }

}
