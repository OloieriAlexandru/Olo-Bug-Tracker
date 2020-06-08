import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { map, catchError } from 'rxjs/operators';

import { GenericService } from './generic.service';

import { UserCredentials } from '../models/UserCredentials';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private URL = environment.baseURL + '/api/v1/';

  constructor(
    private baseService: GenericService,
    private jwtHelper: JwtHelperService
  ) {}

  public login(credentials: UserCredentials) {
    return this.baseService.post(this.URL, 'auth', credentials).pipe(
      map((response: any): any => {
        if (response && response.token) {
          localStorage.setItem('token', response.token);
          return { status: true };
        }
      }),
      catchError((err: any): any => {
        throw new Error(err.error.message);
      })
    );
  }

  public logout() {
    localStorage.removeItem('token');
  }

  public isLoggedIn(): boolean {
    const token: string = this.jwtHelper.tokenGetter();

    if (!token) {
      return false;
    }

    if (this.jwtHelper.isTokenExpired()) {
      localStorage.removeItem('token');
      return false;
    }

    return true;
  }
}
