import { Component, OnInit, OnDestroy } from '@angular/core';
import { DataService, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-promptlet-detail',
  templateUrl: './promptlet-detail.component.html',
  styleUrls: ['./promptlet-detail.component.css']
})
export class PromptletDetailComponent implements OnInit, OnDestroy {

  sharedData: SharedData;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    this.dataService.getUserResponse(this.sharedData.currentPromptlet.userResponses.map(x => x.id))
    this.dataService.fetchUserResponse();
    // this.dataService.getUserResponse(this.sharedData.currentPromptlet.userResponses);
  }

  ngOnDestroy(): void {
    this.dataService.disconnectUserResponse();
  }
}
