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

    this.dataService.updateProfileAndClasses();
  }

  public routeToClass(destClass: ClassData) {
    this.dataService.setCurrentClass(destClass);
    this.router.navigate(['class'], { relativeTo: this.route });
  }
  
  public routeToEnrolledClass(destEnrolledClass: ClassData) {
    this.dataService.setCurrentEnrolledClass(destEnrolledClass);
    this.router.navigate(['enrolledClass'], { relativeTo: this.route });
  }

}
