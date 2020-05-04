import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { GenericService } from './generic.service';

import { ProjectGetAll } from '../models/ProjectGetAll';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private URL = environment.baseURL + '/api/v1/projects/';

  constructor(private baseService: GenericService) {}

  public getAll(): Observable<ProjectGetAll[]> {
    return this.baseService.get<ProjectGetAll[]>(this.URL, '');
  }
}
