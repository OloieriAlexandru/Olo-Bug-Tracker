import { Injectable } from '@angular/core';

import { GenericService } from './generic.service';

import { UserNew } from '../models/UserNew';
import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private URL = environment.baseURL + '/api/v1/users/';

  constructor(private baseService: GenericService) {}

  public createUser(user: UserNew): Observable<UserNew> {
    return this.baseService.post<UserNew>(this.URL, 'register');
  }
}
