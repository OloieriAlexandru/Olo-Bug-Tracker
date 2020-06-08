import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'bugsProjects'
})
export class BugsProjectsPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
