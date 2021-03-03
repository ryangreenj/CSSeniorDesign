import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ClassData, DataService, loginResponse, Profile, SharedData} from '../data.service';
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

  sharedData: SharedData;

  constructor(private router: Router, private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
  }

  onSubmit() {
    this.getLogin(this.profileForm.get('username').value, this.profileForm.get('password').value);
    return true;
  }

  getLogin(username: string, password: string){

    this.dataService.login(username,password, this.router);
  }

}
