import { Component, OnInit } from '@angular/core';

import { BugService } from 'src/app/services/bug.service';
import { AppService } from 'src/app/services/app.service';

import { BugGetAllProjectInfo } from 'src/app/models/BugGetAllProjectInfo';
import { BugCreateDTOFe } from 'src/app/models/BugCreateDTOFe';
import { BugStatus } from 'src/app/models/BugStatus';

@Component({
  selector: 'app-bugs',
  templateUrl: './bugs.component.html',
  styleUrls: ['./bugs.component.scss'],
})
export class BugsComponent implements OnInit {
  public projectBugs: BugGetAllProjectInfo[] = null;
  public bugsStatuses: BugStatus[] = null;

  public filterProjectName: String = '';
  public filterBugStatus: String = '';

  constructor(private bugService: BugService, private appService: AppService) {}

  ngOnInit(): void {
    this.bugService
      .getAll()
      .subscribe((projectBugs: BugGetAllProjectInfo[]) => {
        this.projectBugs = projectBugs;
        this.appService.getCreatedBugNotification().subscribe((res) => {
          for (let bug of res) {
            this.addBugToArray(bug);
          }
        });
      });

    this.bugsStatuses = this.appService.getBugsStatuses();
  }

  public onProjectChange(newProjectName) {
    this.filterProjectName = newProjectName;
  }

  public onBugStatusChange(newBugStatus) {
    this.filterBugStatus = newBugStatus;
  }

  private addBugToArray(bug: BugCreateDTOFe) {
    let projectBugIndex = this.projectBugs.findIndex(
      (projBug: BugGetAllProjectInfo) => {
        projBug.id == bug.projectId;
      }
    );

    if (projectBugIndex != -1) {
      this.projectBugs[projectBugIndex].bugs.push(bug.bug);
      return;
    }

    this.projectBugs.push({
      id: bug.projectId,
      name: bug.projectName,
      bugs: [bug.bug],
    });
  }
}
