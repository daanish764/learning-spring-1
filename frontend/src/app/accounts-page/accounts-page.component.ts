import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-accounts-page',
  templateUrl: './accounts-page.component.html',
  styleUrls: ['./accounts-page.component.css']
})
export class AccountsPageComponent {

  accounts:any = null


  constructor(private http: HttpClient) { }

  ngOnInit() {
    const url = 'http://localhost:8080/api/accounts'

    const headers = {
      'Content-Type': "application/json"
     }


    this.http.get(url, { headers: headers, withCredentials: true }).subscribe({
      next: (response) => {this.accounts = response},
      error: (err) => {
        console.log(err);
      }
    })
  }

  routeToAccountDetailPage(account: any) {
    console.log(account);
    console.log("need to implement ")
  }


}
