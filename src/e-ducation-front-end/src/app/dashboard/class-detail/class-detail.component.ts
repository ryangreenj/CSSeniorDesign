import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService, SharedData } from 'src/app/data.service';
import { CreateSessionDialogComponent } from 'src/app/dialog/create-session-dialog/create-session-dialog.component';

@Component({
  selector: 'app-class-detail',
  templateUrl: './class-detail.component.html',
  styleUrls: ['./class-detail.component.css']
})
export class ClassDetailComponent implements OnInit {
  
  sharedData: SharedData;
  
  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.sharedData = data);
  }
  
  public routeToSession(destSession: string) {
    this.sharedData.currentSessionId = destSession;
    this.sharedData.currentSession = this.dataService.loadSessionData(destSession);
    this.dataService.changeData(this.sharedData);
    this.router.navigate(['session'], { relativeTo: this.route });
  }
  
  openCreateSessionDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    let dialogRef = this.dialog.open(CreateSessionDialogComponent, dialogConfig);
    
    dialogRef.afterClosed().subscribe(result => {
      
    })
  }
  
}
