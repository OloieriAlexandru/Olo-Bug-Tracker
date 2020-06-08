import { Component, Input } from '@angular/core';
import { BugGetAll } from 'src/app/models/BugGetAll';

@Component({
  selector: 'app-table-line-bug',
  templateUrl: './table-line-bug.component.html',
  styleUrls: ['./table-line-bug.component.scss'],
})
export class TableLineBugComponent {
  @Input() public bug: BugGetAll = null;
  @Input() public projectName: String = null;

  constructor() {}
}
