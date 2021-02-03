import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClassData, DataService, SharedData } from "../../data.service";

@Component({
  selector: 'app-class-list',
  templateUrl: './class-list.component.html',
  styleUrls: ['./class-list.component.css']
})
export class ClassListComponent implements OnInit {
  
  sharedData: SharedData;
  
  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data)
  }
  
  public routeToClass(destClass: ClassData) {
    this.sharedData.currentClass = destClass;
    this.dataService.changeData(this.sharedData);
    this.router.navigate(['../class'], { relativeTo: this.route });
    
  }
}
