import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-enroll-class-dialog',
  templateUrl: './enroll-class-dialog.component.html',
  styleUrls: ['./enroll-class-dialog.component.css']
})
export class EnrollClassDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EnrollClassDialogComponent>) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }

}
