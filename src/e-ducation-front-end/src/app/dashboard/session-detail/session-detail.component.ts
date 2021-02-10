import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-session-detail',
  templateUrl: './session-detail.component.html',
  styleUrls: ['./session-detail.component.css']
})
export class SessionDetailComponent implements OnInit {
  
  sharedData: SharedData;
  
  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
  }
  
  public routeToPromptlet(destPromptlet: string) {
    this.sharedData.currentPromptletId = destPromptlet;
    this.sharedData.currentPromptlet = this.dataService.loadPromptletData(destPromptlet);
    this.dataService.changeData(this.sharedData);
    this.router.navigate(['promptlet'], { relativeTo: this.route });
  }
  
}