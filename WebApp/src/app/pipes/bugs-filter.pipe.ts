import { Pipe, PipeTransform } from '@angular/core';

import { BugGetAllProjectInfo } from '../models/BugGetAllProjectInfo';

@Pipe({
  name: 'bugsFilter',
  pure: false,
})
export class BugsFilterPipe implements PipeTransform {
  transform(
    projectsBugs: BugGetAllProjectInfo[],
    projectName: String
  ): BugGetAllProjectInfo[] {
    if (projectsBugs == null) {
      return null;
    }

    if (projectName == null || projectName == '') {
      return projectsBugs;
    }

    return projectsBugs.filter((projectBug: BugGetAllProjectInfo) => {
      return projectBug.name == projectName;
    });
  }
}
