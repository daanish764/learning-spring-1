import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  // this is the behaviour subject that holds and broadcasts login state
  private isAuthenticated = new BehaviorSubject<boolean>(false);

    // Observable for other components to subscribe to
  isAuthenticated$ = this.isAuthenticated.asObservable();

  constructor() { }

  login(sessionId : string) {
    localStorage.setItem("sessionId", sessionId);
    this.isAuthenticated.next(true);
  }

  logout() {
    localStorage.removeItem("sessionId");
    this.isAuthenticated.next(false);
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
