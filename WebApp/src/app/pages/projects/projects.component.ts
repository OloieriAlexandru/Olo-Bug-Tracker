import { Component, OnInit } from '@angular/core';

import { AppService } from 'src/app/services/app.service';
import { ProjectService } from 'src/app/services/project.service';

import { ProjectGetAll } from 'src/app/models/ProjectGetAll';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss'],
})
export class ProjectsComponent implements OnInit {
  public projects: ProjectGetAll[] = null;

  constructor(
    private projectService: ProjectService,
    private appService: AppService
  ) {}

  ngOnInit(): void {
    this.projectService.getAll().subscribe((projects: ProjectGetAll[]) => {
      this.projects = projects;
      this.appService.getCreatedProjectNotification().subscribe((res) => {
        for (let proj of res) {
          this.projects.push(proj);
        }
      });
    });
  }
}
