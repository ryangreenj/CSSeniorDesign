import { Component, OnInit, OnDestroy } from '@angular/core';
import { DataService, SharedData } from 'src/app/data.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-promptlet-detail',
  templateUrl: './promptlet-detail.component.html',
  styleUrls: ['./promptlet-detail.component.css']
})
export class PromptletDetailComponent implements OnInit, OnDestroy {

  sharedData: SharedData;

  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    this.dataService.getUserResponse(this.sharedData.currentPromptlet.userResponses.map(x => x.id))
    this.dataService.fetchUserResponse();
  }

  ngOnDestroy(): void {
    this.dataService.disconnectUserResponse();
  }
  
  goBack(): void {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
}
