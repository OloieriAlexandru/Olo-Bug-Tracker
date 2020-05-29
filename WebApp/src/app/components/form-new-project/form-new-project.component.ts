import { Component, Output, EventEmitter } from '@angular/core';

import { ProjectService } from 'src/app/services/project.service';

import { ProjectCreate } from 'src/app/models/ProjectCreate';
import { ProjectGetAll } from 'src/app/models/ProjectGetAll';
import { ProjectGetById } from 'src/app/models/ProjectGetById';

@Component({
  selector: 'app-form-new-project',
  templateUrl: './form-new-project.component.html',
  styleUrls: ['./form-new-project.component.scss'],
})
export class FormNewProjectComponent {
  public newProject: ProjectCreate = new ProjectCreate();
  @Output() public onCreated: EventEmitter<ProjectGetAll> = new EventEmitter();

  constructor(private projectService: ProjectService) {}

  public create() {
    this.projectService.create(this.newProject).subscribe(
      (createdProject: ProjectGetById) => {
        this.onCreated.emit({
          id: createdProject.id,
          name: createdProject.name,
          shortDescription: createdProject.description.substr(0, 100),
        });
        this.newProject = new ProjectCreate();
      },
      (err) => {}
    );
  }
}
