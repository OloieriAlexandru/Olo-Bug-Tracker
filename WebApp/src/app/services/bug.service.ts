import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { GenericService } from './generic.service';

import { BugCreate } from '../models/BugCreate';
import { BugGetById } from '../models/BugGetById';
import { BugGetAllProjectInfo } from '../models/BugGetAllProjectInfo';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BugService {
  private URL = environment.baseURL + '/api/v1/';

  constructor(private baseService: GenericService) {}

  public create(projectId: Number, newBug: BugCreate): Observable<BugGetById> {
    return this.baseService.post<BugGetById>(
      this.URL,
      'projects/' + projectId + '/bugs',
      newBug
    );
  }

  public getAll(): Observable<BugGetAllProjectInfo[]> {
    return this.baseService.get<BugGetAllProjectInfo[]>(this.URL, 'bugs');
  }
}
