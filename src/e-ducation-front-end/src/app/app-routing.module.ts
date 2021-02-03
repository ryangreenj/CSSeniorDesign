import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component'
import { ClassListComponent } from './dashboard/class-list/class-list.component';
import { ClassDetailComponent } from './dashboard/class-detail/class-detail.component';
import { SessionDetailComponent } from './dashboard/session-detail/session-detail.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { 
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: 'classlist', component: ClassListComponent },
      { path: 'class', component: ClassDetailComponent },
      { path: 'class/session', component: SessionDetailComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }