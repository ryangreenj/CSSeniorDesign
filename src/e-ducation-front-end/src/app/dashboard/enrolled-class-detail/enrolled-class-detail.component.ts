import {Component, OnDestroy, OnInit} from '@angular/core';
import { DataService, Promptlet, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-enrolled-class-detail',
  templateUrl: './enrolled-class-detail.component.html',
  styleUrls: ['./enrolled-class-detail.component.css']
})
export class EnrolledClassDetailComponent implements OnInit, OnDestroy {

  sharedData: SharedData;

  activePromptlets: Promptlet[];

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    this.dataService.updateProfileAndClasses();
    console.log(this.sharedData.enrolledClasses)
    // this.dataService.loadPromptletsByActiveSession();
    this.dataService.fetchPromptletData();
  }

  ngOnDestroy(): void {
    // this.dataService.setCurrentClass(undefined);
    this.dataService.disconnectPromptlets();
  }

}
