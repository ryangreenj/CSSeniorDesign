import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {DataService, idReturnType, loginResponse, Profile, SharedData} from '../data.service';
import { PasswordMatchValidator } from './passwordValidator';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit {

  minPwChar: number = 8;
  formGroup: FormGroup;

  username: string;
  password: string;

  sharedData: SharedData;

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email
  ]);

  constructor(private router: Router, private formBuilder: FormBuilder, private dataService: DataService) {}

  ngOnInit() {
    this.dataService.currentData.subscribe(data => this.sharedData = data);

    this.formGroup = this.formBuilder.group({
      password1: ['', [Validators.required, Validators.minLength(this.minPwChar)]],
      password2: ['', [Validators.required]]
    }, {validator: PasswordMatchValidator});
  }

  get password1() { return this.formGroup.get('password1'); }
  get password2() { return this.formGroup.get('password2'); }

  onPasswordInput() {
    if (this.formGroup.hasError('passwordMismatch'))
      this.password2.setErrors([{'passwordMismatch': true}]);
    else
      this.password2.setErrors(null);
  }

  onSubmit() {
    this.dataService.createProfile(this.username)
      .subscribe((profileData: idReturnType) =>
      {
        this.dataService.createUser(this.username,this.password, profileData.id)
          .subscribe((newUserData: string) =>
          {
            this.dataService.login(this.username,this.password, this.router);
          });
      });
  }
}
