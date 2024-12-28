import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { AuthService } from "./auth.service";
import { Router } from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    isUsingBasicAuth = false;
    isUsingTokenAuth = true;

    constructor(private authService : AuthService,
        private router : Router
    ) {

    }

    intercept(req: HttpRequest<any>, next: HttpHandler) :Observable<HttpEvent<any>> {

        if(req.urlWithParams.match('/auth/logout')) {
            // dont need request header for logout request
            return next.handle(req);
        }

        let authHeader = null 
        if(this.isUsingBasicAuth) {
            authHeader = this.authService.getAuthorizationHeader();
        }

        if(this.isUsingTokenAuth) {
            authHeader = this.authService.getTokenHeader();
        }

        console.log(authHeader)
        if(authHeader) {
            // clone request and add auth header 
            const clonedRequest = req.clone({
                setHeaders: {
                    Authorization: authHeader
                }
            })

            return next.handle(clonedRequest).pipe(
                catchError(err => {
                    console.error(err);
                    if(err.status === 401 || err.status === 403) {
                        // if the response is unauthroized
                        console.log(this.router.url)
                        if(this.router.url !== '/login' &&   this.router.url !== '/register') {
                            this.authService.logout()
                            console.log("redirecting to login page");
                            this.router.navigate(['/login']);
                        }
                    }
                    return throwError('error!')
                }))
        }
        else {
            // this is no auth header 
            // ask user to enter credentials again. redirect to login page
            console.trace()
            this.authService.logout()
            console.log("NAVIGATE to /login")
            this.router.navigate(['/login']);
        }

        
        console.log(req);
        return next.handle(req);
    }

}