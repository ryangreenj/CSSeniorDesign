import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PasswordMatchValidator1, PasswordMatchValidator2 } from './passwordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  
  minPwChar: number = 8;
  formGroup1: FormGroup;
  formGroup2: FormGroup;
  
  emailFormControl1 = new FormControl('', [
    Validators.required,
    Validators.email
  ]);
  
  emailFormControl2 = new FormControl('', [
    Validators.required,
    Validators.email
  ]);
  
  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.formGroup1 = this.formBuilder.group({
      password1: ['', [Validators.required, Validators.minLength(this.minPwChar)]],
      password2: ['', [Validators.required]]
    }, {validator: PasswordMatchValidator1});
    
    this.formGroup2 = this.formBuilder.group({
      password3: ['', [Validators.required, Validators.minLength(this.minPwChar)]],
      password4: ['', [Validators.required]]
    }, {validator: PasswordMatchValidator2});
  }
  
  get password1() { return this.formGroup1.get('password1'); }
  get password2() { return this.formGroup1.get('password2'); }
  get password3() { return this.formGroup2.get('password3'); }
  get password4() { return this.formGroup2.get('password4'); }
  
  onPasswordInput1() {
    if (this.formGroup1.hasError('passwordMismatch'))
      this.password2.setErrors([{'passwordMismatch': true}]);
    else
      this.password2.setErrors(null);
  }
  
  onPasswordInput2() {
    if (this.formGroup2.hasError('passwordMismatch'))
      this.password4.setErrors([{'passwordMismatch': true}]);
    else
      this.password4.setErrors(null);
  }
  
}