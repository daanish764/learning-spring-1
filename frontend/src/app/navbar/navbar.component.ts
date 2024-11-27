import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  isLoggedIn = false 

  constructor(private authService : AuthService, private router : Router) {}

  ngOnInit() {
   this.authService.isAuthenticated$.subscribe(authState => {
    this.isLoggedIn = authState;
   })
  }

  

  logout() {
    console.log('Navbar.logout() called')

    this.authService.logout();
  }



}
