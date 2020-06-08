import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable } from 'rxjs';

import { ProjectGetAll } from '../models/ProjectGetAll';
import { BugCreateDTOFe } from '../models/BugCreateDTOFe';
import { BugStatus } from '../models/BugStatus';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  private createdProject: BehaviorSubject<
    ProjectGetAll[]
  > = new BehaviorSubject([]);

  private createdBug: BehaviorSubject<BugCreateDTOFe[]> = new BehaviorSubject(
    []
  );

  constructor() {}

  public sendCreatedProjectNotification(newProject: ProjectGetAll) {
    this.createdProject.next([newProject]);
  }

  public getCreatedProjectNotification(): Observable<ProjectGetAll[]> {
    return this.createdProject.asObservable();
  }

  public sendCreatedBugNotification(newBug: BugCreateDTOFe) {
    this.createdBug.next([newBug]);
  }

  public getCreatedBugNotification(): Observable<BugCreateDTOFe[]> {
    return this.createdBug.asObservable();
  }

  public getBugsStatuses(): BugStatus[] {
    return [
      { displayName: 'Open', value: 'OPEN' },
      { displayName: 'In progress', value: 'IN_PROGRESS' },
      { displayName: 'Not a bug', value: 'NOT_A_BUG' },
      { displayName: 'Not reproducible', value: 'NOT_REPRODUCIBLE' },
      { displayName: 'Missing information', value: 'MISSING_INFORMATION' },
      {
        displayName: 'Ready for next release',
        value: 'READY_FOR_NEXT_RELEASE',
      },
      { displayName: 'Ready for retest', value: 'READY_FOR_RETEST' },
      { displayName: 'Closed', value: 'CLOSED' },
      { displayName: 'On hold', value: 'ON_HOLD' },
      { displayName: 'Duplicate bug', value: 'DUPLICATE_BUG' },
    ];
  }
}
