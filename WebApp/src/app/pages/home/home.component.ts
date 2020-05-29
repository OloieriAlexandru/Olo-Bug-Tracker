import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ProjectGetAll } from 'src/app/models/ProjectGetAll';
import { AppService } from 'src/app/services/app.service';

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

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private appService: AppService
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
      this.newProjectFormOpen = !this.newProjectFormOpen;
    } else if (this.currentPage == this.pages[1].value) {
      this.newBugFormOpen = !this.newBugFormOpen;
    }
  }

  public onOptionChanged(newOption) {
    this.currentPage = newOption;
    this.router.navigate(['./' + newOption], { relativeTo: this.route });
  }

  public onProjectCreated(newProject: ProjectGetAll) {
    this.appService.sendCreatedProjectNotification(newProject);
    this.newProjectFormOpen = false;
  }

  public logout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }
}
