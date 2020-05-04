import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@Angular/forms';

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

import { AppComponent } from './app.component';
import { AuthComponent } from './pages/auth/auth.component';
import { HomeComponent } from './pages/home/home.component';

export function tokenGetter() {
  return localStorage.getItem('token');
}

@NgModule({
  declarations: [AppComponent, AuthComponent, HomeComponent],
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
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
