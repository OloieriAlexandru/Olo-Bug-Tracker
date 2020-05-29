import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuardService } from './services/auth-guard.service';
import { AuthNotGuardService } from './services/auth-not-guard.service';

import { AuthComponent } from './pages/auth/auth.component';
import { HomeComponent } from './pages/home/home.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { BugsComponent } from './pages/bugs/bugs.component';

const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent,
    canActivate: [AuthNotGuardService],
  },
  {
    path: 'app',
    component: HomeComponent,
    canActivate: [AuthGuardService],
    children: [
      { path: '', redirectTo: 'projects', pathMatch: 'full' },
      { path: 'projects', component: ProjectsComponent },
      { path: 'bugs', component: BugsComponent },
    ],
  },
  {
    path: '',
    redirectTo: '/auth',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
