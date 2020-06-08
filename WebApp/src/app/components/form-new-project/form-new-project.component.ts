import { Component, Output, EventEmitter } from '@angular/core';

import { ProjectService } from 'src/app/services/project.service';

import { ProjectCreate } from 'src/app/models/ProjectCreate';
import { ProjectGetById } from 'src/app/models/ProjectGetById';

@Component({
  selector: 'app-form-new-project',
  templateUrl: './form-new-project.component.html',
  styleUrls: ['./form-new-project.component.scss'],
})
export class FormNewProjectComponent {
  @Output() public onCreated: EventEmitter<any> = new EventEmitter();
  public newProject: ProjectCreate = new ProjectCreate();

  constructor(private projectService: ProjectService) {}

  public create(addAnother: boolean) {
    this.projectService.create(this.newProject).subscribe(
      (createdProject: ProjectGetById) => {
        let close = true;
        if (addAnother) {
          close = false;
        }

        this.onCreated.emit({
          project: {
            id: createdProject.id,
            name: createdProject.name,
            shortDescription: createdProject.description.substr(0, 100),
          },
          close: close,
        });

        this.newProject = new ProjectCreate();
      },
      (err) => {}
    );
  }
}
