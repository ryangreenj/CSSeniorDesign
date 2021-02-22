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
  classData: ClassData[] = [];

  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);

    this.testGetAllClasses();
    // Load class data for user
    this.sharedData.classes = this.dataService.loadClassData();
    this.dataService.changeData(this.sharedData);
  }

  public routeToClass(destClass: ClassData) {
    this.sharedData.currentClass = destClass;
    // this.dataService.changeData(this.sharedData);
    this.router.navigate(['class'], { relativeTo: this.route });
  }

  testGetAllClasses(){
    this.dataService.getClassData()
      .subscribe((data: ClassData[]) =>
      {
        let dd = { ... data};
        let i = 0;
        while (true) {
          let d = dd[i];
          if (d != undefined){
            this.classData.push(d as ClassData);
            i += 1;
          } else {
            break
          }
        }});
  }
}
