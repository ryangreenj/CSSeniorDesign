import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
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

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
    this.dataService.loadPromptletsByCurrentSessionId();
    this.dataService.fetchPromptletData();
  }

  public routeToPromptlet(destPromptlet: string) {
    this.sharedData.currentPromptletId = destPromptlet;
    this.sharedData.currentPromptlet = this.dataService.loadPromptletData(destPromptlet);
    this.dataService.changeData(this.sharedData);
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

}
