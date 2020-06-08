import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import * as M from 'materialize-css/dist/js/materialize';

import { JwtModule } from '@auth0/angular-jwt';

import { AppRoutingModule } from './app-routing.module';

import { GenericService } from './services/generic.service';
import { AuthService } from './services/auth.service';
import { JwtHttpInterceptorService } from './services/jwt-http-interceptor.service';
import { AuthGuardService } from './services/auth-guard.service';
import { AuthNotGuardService } from './services/auth-not-guard.service';
import { UserService } from './services/user.service';
import { ProjectService } from './services/project.service';
import { AppService } from './services/app.service';

import { BugsFilterPipe } from './pipes/bugs-filter.pipe';

import { AppComponent } from './app.component';
import { AuthComponent } from './pages/auth/auth.component';
import { HomeComponent } from './pages/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { BugsComponent } from './pages/bugs/bugs.component';
import { FormNewProjectComponent } from './components/form-new-project/form-new-project.component';
import { FormNewBugComponent } from './components/form-new-bug/form-new-bug.component';
import { CardProjectComponent } from './components/card-project/card-project.component';
import { TableLineBugComponent } from './components/table-line-bug/table-line-bug.component';
import { BugsProjectsPipe } from './pipes/bugs-projects.pipe';

export function tokenGetter() {
  return localStorage.getItem('token');
}

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    HomeComponent,
    NavbarComponent,
    ProjectsComponent,
    BugsComponent,
    FormNewProjectComponent,
    FormNewBugComponent,
    CardProjectComponent,
    TableLineBugComponent,
    BugsFilterPipe,
    BugsProjectsPipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
      },
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtHttpInterceptorService,
      multi: true,
    },
    { provide: 'M', useValue: M },
    GenericService,
    UserService,
    ProjectService,
    AuthService,
    AuthGuardService,
    AuthNotGuardService,
    AppService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
