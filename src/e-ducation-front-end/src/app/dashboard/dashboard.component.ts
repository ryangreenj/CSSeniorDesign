import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  classes: { id: number, code: string, name: string, status: boolean }[] = [
    { "id": 100, "code": "CS101", "name": "Computer Science 1", "status": false },
    { "id": 101, "code": "EECE3093C", "name": "Software Engineering", "status": false },
    { "id": 102, "code": "COMM1017", "name": "Intro to Public Speaking", "status": false },
    { "id": 103, "code": "SPN1001", "name": "Spanish 1", "status": true }
  ];
  
  constructor() { }

  ngOnInit(): void {
    // TODO: Load available classes from database
  }
  
  

}
