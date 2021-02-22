import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ClassData, DataService, loginResponse} from '../data.service';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  profileForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  // username: string;
  // password: string;

  constructor(private router: Router, private dataService: DataService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.getLogin(this.profileForm.get('username').value, this.profileForm.get('password').value);
    // console.log(this.username,this.password);

    return true;
  }

  getLogin(username: string, password: string){

    this.dataService.loginUser(username,password)
      .subscribe((data: loginResponse) =>
      {
        this.dataService.setJwt(data.jwt);
        this.router.navigate(["dashboard/classlist"]);
      });
    return true;
  }

}
