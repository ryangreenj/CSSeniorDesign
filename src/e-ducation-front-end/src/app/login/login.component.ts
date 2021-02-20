import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  username: string;
  password: string;
  
  constructor(private router: Router, private dataService: DataService) { }

  ngOnInit(): void {
  }
  
  onSubmit() {
    if (this.dataService.loginUser(this.username, this.password)) {
      this.router.navigate(["dashboard/classlist"])
    }
  }

}
