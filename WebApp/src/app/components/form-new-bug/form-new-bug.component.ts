import { Component, Input, Inject, EventEmitter, Output } from '@angular/core';
import { DOCUMENT } from '@angular/common';

import { BugService } from 'src/app/services/bug.service';

import { BugCreate } from 'src/app/models/BugCreate';
import { ProjectGetAll } from 'src/app/models/ProjectGetAll';
import { BugGetById } from 'src/app/models/BugGetById';

@Component({
  selector: 'app-form-new-bug',
  templateUrl: './form-new-bug.component.html',
  styleUrls: ['./form-new-bug.component.scss'],
})
export class FormNewBugComponent {
  @Input() public projects: ProjectGetAll[];
  @Output() public onCreated: EventEmitter<any> = new EventEmitter();
  public newBug: BugCreate = new BugCreate();

  public projectId: Number = null;
  public projectName: String = null;

  public priorities: any[] = [
    { displayName: 'Low', value: 'LOW' },
    { displayName: 'Medium', value: 'MEDIUM' },
    { displayName: 'High', value: 'HIGH' },
    { displayName: 'Critical', value: 'CRITICAL' },
  ];

  public userRoles: any[] = [
    { displayName: 'All', value: 'ALL' },
    { displayName: 'Owner', value: 'OWNER' },
    { displayName: 'Developer', value: 'DEVELOPER' },
    { displayName: 'Tester', value: 'TESTER' },
    { displayName: 'DevOps', value: 'DEV_OPS' },
  ];

  constructor(
    @Inject(DOCUMENT) document,
    @Inject('M') private M: any,
    private bugService: BugService
  ) {}

  public onBugProjectChange(projectId) {
    if (projectId && projectId != '') {
      this.initDatepickers();

      let project = this.projects.find((project: ProjectGetAll) => {
        return project.id == projectId;
      });
      if (project != null) {
        this.projectName = project.name;
      }
    }
    this.projectId = projectId;
  }

  public onResponsibleChange(responsible) {
    this.newBug.userRole = responsible;
  }

  public onDueDateChange(date) {
    this.newBug.dueDate = new Date(date);
  }

  public create(addAnother: boolean) {
    this.bugService.create(this.projectId, this.newBug).subscribe(
      (bug: BugGetById) => {
        let close = true;
        if (addAnother) {
          close = false;
        }

        this.onCreated.emit({
          bug: {
            id: bug.id,
            status: bug.status,
            priority: bug.priority,
            title: bug.title,
            dueDate: bug.dueDate,
          },
          projectInfo: {
            id: this.projectId,
            name: this.projectName,
          },
          close: close,
        });

        this.newBug = new BugCreate();
      },
      (err) => {
        console.log(err);
      }
    );
  }

  private initDatepickers() {
    setTimeout(() => {
      this.M.Datepicker.init(document.querySelectorAll('.datepicker'), {});
    }, 1000);
  }
}
