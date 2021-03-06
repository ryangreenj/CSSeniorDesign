import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatSliderModule } from '@angular/material/slider';
import { MatTableModule } from "@angular/material/table";
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ClassListComponent } from './dashboard/class-list/class-list.component';
import { ClassDetailComponent } from './dashboard/class-detail/class-detail.component';
import { SessionDetailComponent } from './dashboard/session-detail/session-detail.component';
import { PromptletDetailComponent } from './dashboard/promptlet-detail/promptlet-detail.component';
import { CreateClassDialogComponent } from './dialog/create-class-dialog/create-class-dialog.component';
import { EnrollClassDialogComponent } from './dialog/enroll-class-dialog/enroll-class-dialog.component';
import { CreateSessionDialogComponent } from './dialog/create-session-dialog/create-session-dialog.component';
import { CreatePromptletDialogComponent } from './dialog/create-promptlet-dialog/create-promptlet-dialog.component';
import { HttpClientModule } from "@angular/common/http";
import { PromptletDisplayComponent } from './promptlet/promptlet-display/promptlet-display.component';
import { EnrolledClassDetailComponent } from './dashboard/enrolled-class-detail/enrolled-class-detail.component';
import { ResultsDisplayComponent } from './promptlet/results-display/results-display.component';
import { UserResponseListComponent } from './promptlet/user-response-list/user-response-list.component';
import { ClassListDisplayComponent } from './dashboard/class-list/class-list-display/class-list-display.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    ClassListComponent,
    ClassDetailComponent,
    SessionDetailComponent,
    PromptletDetailComponent,
    CreateClassDialogComponent,
    EnrollClassDialogComponent,
    CreateSessionDialogComponent,
    CreatePromptletDialogComponent,
    PromptletDisplayComponent,
    EnrolledClassDetailComponent,
    ResultsDisplayComponent,
    UserResponseListComponent,
    ClassListDisplayComponent
  ],
  entryComponents: [
    CreateClassDialogComponent,
    EnrollClassDialogComponent,
    CreateSessionDialogComponent,
    CreatePromptletDialogComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatCardModule,
        MatCheckboxModule,
        FormsModule,
        ReactiveFormsModule,
        MatSidenavModule,
        MatDialogModule,
        MatIconModule,
        MatSelectModule,
        HttpClientModule,
        MatRadioModule,
        MatSliderModule,
        MatTableModule,
        NgxChartsModule,
        MatButtonToggleModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
