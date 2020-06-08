export class BugCreate {
  title: String;
  description: String;
  priority: String;
  userRole: String;
  dueDate: Date;

  constructor() {
    this.userRole = 'ALL';
  }
}
