import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-class-detail',
  templateUrl: './class-detail.component.html',
  styleUrls: ['./class-detail.component.css']
})
export class ClassDetailComponent implements OnInit {
  
  sharedData: SharedData;
  
  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
  }
  
  public routeToSession(destSession: string) {
    this.sharedData.currentSessionId = destSession;
    this.sharedData.currentSession = this.dataService.loadSessionData(destSession);
    this.dataService.changeData(this.sharedData);
    this.router.navigate(['session'], { relativeTo: this.route });
  }
  
}
