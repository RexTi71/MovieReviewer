import { HttpErrorResponse, HttpEventType, HttpInterceptorFn } from '@angular/common/http';
import { catchError, tap, throwError } from 'rxjs';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const errorHandlerInterceptor: HttpInterceptorFn = (req, next) => {
  const UNATHORIZED:number = 401;
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === UNATHORIZED) {
        console.log('Wygasł token! Wylogowuję użytkownika.');

        sessionStorage.removeItem('token');

        router.navigate(['/login']);
      }
      return throwError(() => error);
    }),
  );
};
