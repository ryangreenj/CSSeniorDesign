import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DataService } from '../data.service';
import { PasswordMatchValidator } from './passwordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  
  minPwChar: number = 8;
  formGroup: FormGroup;
  
  username: string;
  password: string;
  
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email
  ]);
  
  constructor(private formBuilder: FormBuilder, private dataService: DataService) {}

  ngOnInit() {
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
    this.dataService.createUser(this.username, this.password);
  }
}