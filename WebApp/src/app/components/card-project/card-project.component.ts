import { Component, OnInit, Input } from '@angular/core';
import { ProjectGetAll } from 'src/app/models/ProjectGetAll';

@Component({
  selector: 'app-card-project',
  templateUrl: './card-project.component.html',
  styleUrls: ['./card-project.component.scss'],
})
export class CardProjectComponent {
  @Input() public project: ProjectGetAll;

  constructor() {}
}
