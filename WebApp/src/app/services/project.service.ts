import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { GenericService } from './generic.service';

import { ProjectGetAll } from '../models/ProjectGetAll';
import { ProjectCreate } from '../models/ProjectCreate';
import { ProjectGetById } from '../models/ProjectGetById';

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

  public create(newProject: ProjectCreate): Observable<ProjectGetById> {
    return this.baseService.post<ProjectGetById>(this.URL, '', newProject);
  }
}
