import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ProjectGetAll } from '../models/ProjectGetAll';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  private clicked: BehaviorSubject<any> = new BehaviorSubject({});
  private createdProject: BehaviorSubject<
    ProjectGetAll[]
  > = new BehaviorSubject([]);

  constructor() {}

  public sendCreatedProjectNotification(newProject: ProjectGetAll) {
    this.createdProject.next([newProject]);
  }

  public getCreatedProjectNotification(): Observable<ProjectGetAll[]> {
    return this.createdProject.asObservable();
  }
}
