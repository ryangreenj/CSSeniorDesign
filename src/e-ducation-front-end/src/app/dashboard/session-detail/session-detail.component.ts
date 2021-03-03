import {Component, OnDestroy, OnInit} from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService, SharedData } from 'src/app/data.service';
import { CreatePromptletDialogComponent } from 'src/app/dialog/create-promptlet-dialog/create-promptlet-dialog.component';

@Component({
  selector: 'app-session-detail',
  templateUrl: './session-detail.component.html',
  styleUrls: ['./session-detail.component.css']
})
export class SessionDetailComponent implements OnInit {

  sharedData: SharedData;
  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute, private dialog: MatDialog) { }

  checked: boolean = true;

  changeValue(id: string,value: boolean) {
    this.dataService.updatePromptletStatus(id, !value);
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    this.dataService.loadPromptletsByCurrentSessionId();
    this.dataService.fetchPromptletData();
  }

  public routeToPromptlet(destPromptlet: string) {

    // this.sharedData.currentPromptlet = {id:destPromptlet, prompt: "", promptlet_type: "MULTI_CHOICE", answerPool: [],
    //         correctAnswer: [], userResponses: []}
    this.dataService.setCurrentPromptlet(destPromptlet);
    // this.dataService.changeData(this.sharedData);
    this.router.navigate(['promptlet'], { relativeTo: this.route });
  }

  openCreatePromptletDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    let dialogRef = this.dialog.open(CreatePromptletDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {

    })
  }

  setActiveSession(sessionId : string){
    this.dataService.setActiveSession(sessionId);
  }
}
