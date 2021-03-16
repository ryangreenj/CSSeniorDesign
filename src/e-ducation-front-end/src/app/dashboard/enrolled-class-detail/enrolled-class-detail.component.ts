import {Component, OnDestroy, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService, Promptlet, SharedData } from 'src/app/data.service';

@Component({
  selector: 'app-enrolled-class-detail',
  templateUrl: './enrolled-class-detail.component.html',
  styleUrls: ['./enrolled-class-detail.component.css']
})
export class EnrolledClassDetailComponent implements OnInit, OnDestroy {

  sharedData: SharedData;

  activePromptlets: Promptlet[];

  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    this.dataService.updateProfileAndClasses();
  }

  ngOnDestroy(): void {
    this.dataService.disconnectPromptlets();
  }
  
  goBack(): void {
    this.router.navigate(['../classlist'], { relativeTo: this.route });
  }
  
}
