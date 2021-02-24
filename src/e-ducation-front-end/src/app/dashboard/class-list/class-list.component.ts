import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { CreateClassDialogComponent } from 'src/app/dialog/create-class-dialog/create-class-dialog.component';
import { EnrollClassDialogComponent } from 'src/app/dialog/enroll-class-dialog/enroll-class-dialog.component';
import {ClassData, DataService, Profile, SharedData} from "../../data.service";

@Component({
  selector: 'app-class-list',
  templateUrl: './class-list.component.html',
  styleUrls: ['./class-list.component.css']
})
export class ClassListComponent implements OnInit {

  sharedData: SharedData;

  constructor(private dataService: DataService, private router: Router, private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => {this.sharedData = data})
  }

  public routeToClass(destClass: ClassData) {
    this.sharedData.currentClass = destClass;
    this.dataService.changeData(this.sharedData);
    this.router.navigate(['../class'], { relativeTo: this.route });
  }

  openCreateClassDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    //dialogConfig.width = "40%";
    let dialogRef = this.dialog.open(CreateClassDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      this.dataService.updateProfileAndClasses();
    })
  }

  openEnrollClassDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    let dialogRef = this.dialog.open(EnrollClassDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      this.dataService.updateProfileAndClasses();
    })
  }
}
