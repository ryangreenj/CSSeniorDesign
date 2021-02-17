import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClassData, DataService, SharedData } from '../data.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  userId: string;
  sharedData: SharedData;
  
  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    
    // Load class data for user
    this.sharedData.classes = this.dataService.loadClassData();
    this.dataService.changeData(this.sharedData);
  }
  
  public routeToClass(destClass: ClassData) {
    this.sharedData.currentClass = destClass;
    this.dataService.changeData(this.sharedData);
    this.router.navigate(['class'], { relativeTo: this.route });
  }
  
}
