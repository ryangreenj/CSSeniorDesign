import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DataService } from 'src/app/data.service';

@Component({
  selector: 'app-enroll-class-dialog',
  templateUrl: './enroll-class-dialog.component.html',
  styleUrls: ['./enroll-class-dialog.component.css']
})
export class EnrollClassDialogComponent implements OnInit {
  
  classCode: string
  
  constructor(public dialogRef: MatDialogRef<EnrollClassDialogComponent>, private dataService: DataService) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
    this.dataService.enrollClass(this.classCode);
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }

}
