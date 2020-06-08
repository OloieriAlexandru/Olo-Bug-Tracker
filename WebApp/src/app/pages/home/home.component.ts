import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AuthService } from 'src/app/services/auth.service';
import { AppService } from 'src/app/services/app.service';
import { ProjectService } from 'src/app/services/project.service';

import { ProjectGetAll } from 'src/app/models/ProjectGetAll';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  public pages: any[] = [
    { display: 'Projects', value: 'projects' },
    { display: 'Bugs', value: 'bugs' },
  ];
  public currentPage: String = 'bugs';

  public newProjectFormOpen: boolean = false;
  public newBugFormOpen: boolean = false;

  public userProjects: ProjectGetAll[] = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private appService: AppService,
    private projectService: ProjectService
  ) {}

  ngOnInit(): void {
    for (let i = 0; i < this.pages.length; ++i) {
      if (this.router.url.search(this.pages[i].value) != -1) {
        this.currentPage = this.pages[i].value;
        break;
      }
    }
  }

  public openForm() {
    if (this.currentPage == this.pages[0].value) {
      this.openNewProjectForm();
    } else if (this.currentPage == this.pages[1].value) {
      this.openNewBugForm();
    }
  }

  private openNewProjectForm(): void {
    this.newProjectFormOpen = !this.newProjectFormOpen;
  }

  private openNewBugForm(): void {
    if (!this.newBugFormOpen) {
      this.projectService.getAll().subscribe((projects: ProjectGetAll[]) => {
        this.userProjects = projects;
        this.newBugFormOpen = !this.newBugFormOpen;
      });
    } else {
      this.newBugFormOpen = !this.newBugFormOpen;
    }
  }

  public onOptionChanged(newOption) {
    this.resetState();
    this.currentPage = newOption;
    this.router.navigate(['./' + newOption], { relativeTo: this.route });
  }

  private resetState(): void {
    this.newProjectFormOpen = false;
    this.newBugFormOpen = false;
  }

  public onProjectCreated(newProject: any) {
    this.appService.sendCreatedProjectNotification(newProject.project);

    if (newProject.close) {
      this.newProjectFormOpen = false;
    }
  }

  public onBugCreated(newBugInfo: any) {
    this.appService.sendCreatedBugNotification({
      projectId: newBugInfo.projectInfo.id,
      projectName: newBugInfo.projectInfo.name,
      bug: newBugInfo.bug,
    });

    if (newBugInfo.close) {
      this.newBugFormOpen = false;
    }
  }

  public logout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }
}
