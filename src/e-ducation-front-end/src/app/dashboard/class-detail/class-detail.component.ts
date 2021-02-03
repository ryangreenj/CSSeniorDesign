import { Component, OnInit } from '@angular/core';
import { DataService, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-class-detail',
  templateUrl: './class-detail.component.html',
  styleUrls: ['./class-detail.component.css']
})
export class ClassDetailComponent implements OnInit {
  
  sharedData: SharedData;
  
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data)
  }
  
}
