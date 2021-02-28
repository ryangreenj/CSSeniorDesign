import { Component, OnInit } from '@angular/core';
import { DataService, Promptlet, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-enrolled-class-detail',
  templateUrl: './enrolled-class-detail.component.html',
  styleUrls: ['./enrolled-class-detail.component.css']
})
export class EnrolledClassDetailComponent implements OnInit {

  sharedData: SharedData;

  activePromptlets: Promptlet[];

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    this.dataService.loadSessionsByCurrentClassId(this.sharedData.currentEnrolledClass);
  }

}
