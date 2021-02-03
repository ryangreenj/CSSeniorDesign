import { Component, OnInit } from '@angular/core';
import { DataService, Session, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-session-detail',
  templateUrl: './session-detail.component.html',
  styleUrls: ['./session-detail.component.css']
})
export class SessionDetailComponent implements OnInit {
  
  sharedData: SharedData;
  
  // Thinking here we will query session data for each class, whenever this route is loaded
  sessionList: Session[];
  
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
  }

}