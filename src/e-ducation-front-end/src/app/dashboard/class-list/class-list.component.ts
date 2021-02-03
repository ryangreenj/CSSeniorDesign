import { Component, OnInit } from '@angular/core';
import { DataService, SharedData } from "../../data.service";

@Component({
  selector: 'app-class-list',
  templateUrl: './class-list.component.html',
  styleUrls: ['./class-list.component.css']
})
export class ClassListComponent implements OnInit {
  
  sharedData: SharedData;
  
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data)
  }

}
