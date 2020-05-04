import { Component, OnInit } from '@angular/core';

import { ProjectService } from 'src/app/services/project.service';
import { ProjectGetAll } from 'src/app/models/ProjectGetAll';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  public projects: ProjectGetAll[];

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.projectService.getAll().subscribe(
      (projects: ProjectGetAll[]) => {
        this.projects = projects;
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
