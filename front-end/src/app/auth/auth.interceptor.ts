// import { Injectable } from '@angular/core';
// import {
//   HttpRequest,
//   HttpHandler,
//   HttpEvent,
//   HttpInterceptor
// } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { UserService } from '../services/user.service';

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   constructor(private userService: UserService) {}

//   intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

//     const user = this.userService.currentUser;
//     if (user && user.access_token) {
//       console.log(user.access_token);  // Add this line
//       request = request.clone({
//         setHeaders: {
//          //access_token: user.access_token
//          Authorization: `Bearer ${user.access_token}`
//         }
//       });
//       console.log(request);
//     }

//     return next.handle(request);
//   }
// }

// import { Injectable } from '@angular/core';
// import {
//   HttpRequest,
//   HttpHandler,
//   HttpEvent,
//   HttpInterceptor,
//   HttpErrorResponse
// } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError } from 'rxjs/operators';
// import { UserService } from '../services/user.service';

// @Injectable()
// export class HttpInterceptorService implements HttpInterceptor {
//   constructor(private userService: UserService) {}

//   intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
//     const user = this.userService.currentUser;

//     if (user && user.access_token) {
//       request = request.clone({
//         setHeaders: {
//           Authorization: `Bearer ${user.access_token}`
//         }
//       });
//     }

//     return next.handle(request).pipe(
//       catchError((error: HttpErrorResponse) => {
//         console.error('HTTP Error:', error);
//         if (error.status === 401) {
//           console.log("error.status === 401")
//         } else {
//           console.log("error.status != 401")
//         }
//         return throwError(error);
//       })
//     );
//   }
// }


import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor{

  constructor(private userService: UserService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authenticationResponse  = this.userService.currentUser;
    console.log(authenticationResponse)
    console.log(localStorage.getItem('User'))
    if (localStorage.getItem('User')) {
      authenticationResponse = JSON.parse(
        localStorage.getItem('User') as string
      );
      console.log(authenticationResponse)
      console.log(authenticationResponse.access_token)
      const authReq = req.clone({
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + authenticationResponse.access_token
        })
      });
      console.log(authReq)
      return this.handleRequest(authReq, next);
    }
    return this.handleRequest(req, next);
  }

  handleRequest(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(tap((event: HttpEvent<any>) => {
      }));
  }
}
