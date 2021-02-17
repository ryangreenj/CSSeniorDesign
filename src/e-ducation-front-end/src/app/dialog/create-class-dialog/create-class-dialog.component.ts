import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-create-class-dialog',
  templateUrl: './create-class-dialog.component.html',
  styleUrls: ['./create-class-dialog.component.css']
})
export class CreateClassDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<CreateClassDialogComponent>) { }

  ngOnInit(): void {
  }
  
  onCreateClick(): void {
    this.dialogRef.close();
  }
  
  onCancelClick(): void {
    this.dialogRef.close();
  }
  
}
